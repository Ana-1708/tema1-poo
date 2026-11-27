package main;

import entities.points.DamPricesPoints;
import entities.points.DamPricesPredictionPoints;
import entities.points.GridLoadPoints;
import entities.points.GridLoadPredictionsPoints;
import entities.points.ImbalacePricesPoints;
import entities.points.ImbalanceVolumesPoints;
import fileio.CommandInput;
import fileio.MarketDataInput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CommandProcessor {
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVAL_MINUTES = 15;

    private final DataValidator validator;
    private final MarketDataRepository repository;
    private boolean importActive = false;

    /**
     * Creates a command processor.
     *
     * @param validator  the validator used for imported data
     * @param repository the repository where data is stored
     */
    public CommandProcessor(final DataValidator validator,
                            final MarketDataRepository repository) {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * Processes a single command and writes the result to the output.
     *
     * @param command the command to process
     * @param output  the output array
     */
    public void process(final CommandInput command, final ArrayNode output) {
        if ("startImport".equals(command.getCommand())) {
            importActive = true;
        } else if ("stopImport".equals(command.getCommand())) {
            importActive = false;
        } else if ("import".equals(command.getCommand()) && importActive) {
            handleImportAndPatch(command, output);
        } else if ("patch".equals(command.getCommand())) {
            handleImportAndPatch(command, output);
        } else if ("get".equals(command.getCommand())) {
            handleGet(command, output);
        }
    }

    private ObjectNode addWarning(final ArrayNode output, final CommandInput command,
                                  final String message) {
        ObjectNode warning = output.addObject();
        warning.put("command", command.getCommand());
        warning.put("timestamp", command.getTimestamp());
        warning.put("status", "WARNING");
        warning.put("message", message);
        return warning;
    }

    private void addImportWarning(final ArrayNode output, final CommandInput command,
                                  final MarketDataInput point, final String dataType,
                                  final int intervalIndex, final String message) {
        ObjectNode warning = addWarning(output, command, message);

        warning.put("id", dataType);
        warning.put("date", point.date());
        warning.put("interval", point.interval().replaceAll("\\s*-\\s*", " - "));
        warning.put("intervalIndex", intervalIndex);
    }

    /**
     * Handles an import or patch command: validates each point and saves the valid ones.
     *
     * @param command the import or patch command
     * @param output  the output array
     */
    public void handleImportAndPatch(final CommandInput command, final ArrayNode output) {
        List<MarketDataInput> importedPoints = command.getParameters().getData();
        boolean isPatch = "patch".equals(command.getCommand());
        for (MarketDataInput point : importedPoints) {
            String dataType;
            if (isPatch) {
                dataType = command.getParameters().getDataType();
            } else {
                dataType = point.id();
            }
            LocalDate date = LocalDate.parse(point.date());
            String startText = point.interval().split("-", 2)[0].trim();
            LocalTime start = LocalTime.parse(startText);
            int minutes = start.getHour() * MINUTES_PER_HOUR + start.getMinute();
            int intervalIndex = minutes / INTERVAL_MINUTES + 1;

            String message;

            if ("DAM_PRICE".equals(dataType)) {
                Double priceAmount = point.priceAmount();
                message = validator.validateDamPrice(priceAmount);
                if (message == null) {
                    repository.saveDamPrices(date,
                            new DamPricesPoints(intervalIndex, priceAmount));
                }
            } else if ("DAM_PRICE_PREDICTION".equals(dataType)) {
                Double spikeProbability = point.spikeProbability();
                Double predictedPriceEurMwh = point.predictedPriceEurMwh();
                message = validator.validateDamPricesPrediction(predictedPriceEurMwh,
                        spikeProbability);
                if (message == null) {
                    DamPricesPredictionPoints savedPoint = new DamPricesPredictionPoints(
                            intervalIndex, predictedPriceEurMwh, spikeProbability);
                    repository.saveDamPricesPrediction(date, savedPoint);
                }
            } else if ("GRID_LOAD".equals(dataType)) {

                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity == null ? null : quantity.doubleValue();

                List<GridLoadPoints> previousPoints =
                        repository.findFirstValidGridLoadPoint(date, intervalIndex);
                Double previousQuantity =
                        previousPoints.isEmpty() ? null : previousPoints.get(0).getQuantity();
                message = validator.validateGridLoad(quantityDouble, previousQuantity);
                if (message == null) {
                    GridLoadPoints savedPoint = new GridLoadPoints(
                            quantityDouble, intervalIndex, point.reservesRequiredMW());
                    repository.saveGridLoad(date, savedPoint);
                }
            } else if ("GRID_LOAD_PREDICTION".equals(dataType)) {
                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity == null ? null : quantity.doubleValue();

                message = validator.validateGridLoadPrediction(quantityDouble,
                        reservesRequiredMW);
                if (message == null) {
                    GridLoadPredictionsPoints savedPoint = new GridLoadPredictionsPoints(
                            intervalIndex, quantityDouble, reservesRequiredMW);
                    repository.saveGridLoadPrediction(date, savedPoint);
                }
            } else if ("IMBALANCE_PRICE".equals(dataType)) {
                Double positiveImbalancePrice = point.positiveImbalancePrice();
                Double negativeImbalancePrice = point.negativeImbalancePrice();
                message = validator.validateImbalancePrices(positiveImbalancePrice,
                        negativeImbalancePrice);
                if (message == null) {
                    ImbalacePricesPoints savedPoint = new ImbalacePricesPoints(
                            intervalIndex, positiveImbalancePrice, negativeImbalancePrice);
                    repository.saveImbalancePrices(date, savedPoint);
                }
            } else if ("IMBALANCE_VOLUME".equals(dataType)) {
                Double positiveImbalanceVolumes = point.positiveImbalanceVol();
                Double negativeImbalanceVolumes = point.negativeImbalanceVol();
                message = validator.validateImbalanceVolumes(positiveImbalanceVolumes,
                        negativeImbalanceVolumes);
                if (message == null) {
                    ImbalanceVolumesPoints savedPoint = new ImbalanceVolumesPoints(
                            intervalIndex, positiveImbalanceVolumes, negativeImbalanceVolumes);
                    repository.saveImbalanceVolumes(date, savedPoint);
                }
            } else {
                continue;
            }
            if (message != null) {
                if (!isPatch) {
                    repository.clearPoint(dataType, date, intervalIndex);
                }
                addImportWarning(output, command, point, dataType, intervalIndex, message);
            }
        }
    }

    private void handleSinglePointGet(final CommandInput command, final ArrayNode output,
                                      final LocalDateTime intervalStart) {
        String dataType = command.getParameters().getDataType();
        String clientTier = command.getParameters().getClientTier();

        LocalDate date = intervalStart.toLocalDate();
        int minutes = intervalStart.getHour() * MINUTES_PER_HOUR + intervalStart.getMinute();
        int intervalIndex = minutes / INTERVAL_MINUTES + 1;
        LocalDateTime intervalEnd = intervalStart.plusMinutes(INTERVAL_MINUTES);

        if (dataType.equals("DAM_PRICE")) {
            DamPricesPoints point = repository.findDamPrices(date, intervalIndex);
            if (point == null) {
                addGetWarning(output, command, intervalStart, intervalEnd);
            } else {
                ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                        intervalStart, intervalEnd, intervalIndex);
                interval.put("priceAmount", point.getPriceAmount());
            }
        } else if (dataType.equals("DAM_PRICE_PREDICTION")) {
            if (clientTier.equals("FREE")) {
                addGetAccessError(output, command, intervalStart, intervalEnd);
            } else {
                DamPricesPredictionPoints point =
                        repository.findDamPricesPrediction(date, intervalIndex);
                if (point == null) {
                    addGetWarning(output, command, intervalStart, intervalEnd);
                } else {
                    ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                            intervalStart, intervalEnd, intervalIndex);
                    interval.put("predictedPriceEurMwh", point.getPredictedPriceEurMwh());
                    interval.put("spikeProbability", point.getSpikeProbability());
                }
            }
        } else if (dataType.equals("GRID_LOAD")) {
            GridLoadPoints point = repository.findGridLoad(date, intervalIndex);
            if (point == null) {
                addGetWarning(output, command, intervalStart, intervalEnd);
            } else {
                ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                        intervalStart, intervalEnd, intervalIndex);
                interval.put("quantity", point.getQuantity());
            }
        } else if (dataType.equals("GRID_LOAD_PREDICTION")) {
            GridLoadPredictionsPoints point =
                    repository.findGridLoadPrediction(date, intervalIndex);
            if (point == null) {
                addGetWarning(output, command, intervalStart, intervalEnd);
            } else {
                ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                        intervalStart, intervalEnd, intervalIndex);
                interval.put("quantity", point.getQuantity());
            }
        } else if (dataType.equals("IMBALANCE_PRICE")) {
            ImbalacePricesPoints point = repository.findImbalancePrices(date, intervalIndex);
            if (point == null) {
                addGetWarning(output, command, intervalStart, intervalEnd);
            } else {
                ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                        intervalStart, intervalEnd, intervalIndex);
                interval.put("positiveImbalancePrice", point.getPositiveImbalancePrice());
                interval.put("negativeImbalancePrice", point.getNegativeImbalancePrice());
            }
        } else if (dataType.equals("IMBALANCE_VOLUME")) {
            ImbalanceVolumesPoints point = repository.findImbalanceVolumes(date, intervalIndex);
            if (point == null) {
                addGetWarning(output, command, intervalStart, intervalEnd);
            } else {
                ObjectNode interval = addSinglePointSuccess(output, command, dataType,
                        intervalStart, intervalEnd, intervalIndex);
                interval.put("positiveImbalanceVol", point.getPositiveImbalanceVol());
                interval.put("negativeImbalanceVol", point.getNegativeImbalanceVol());
            }
        }
    }

    /**
     * Handles a get command for the latest interval, a single interval or a range.
     *
     * @param command the get command
     * @param output  the output array
     */
    public void handleGet(final CommandInput command, final ArrayNode output) {
        String target = command.getParameters().getTarget();
        if ("LATEST".equals(target)) {
            String dataType = command.getParameters().getDataType();
            LocalDateTime intervalStart = repository.findLatestIntervalStart(dataType);

            if (intervalStart != null) {
                handleSinglePointGet(command, output, intervalStart);
            } else {
                String clientTier = command.getParameters().getClientTier();
                if (dataType.equals("DAM_PRICE_PREDICTION") && clientTier.equals("FREE")) {
                    addGetAccessError(output, command, null, null);
                } else {
                    addGetWarning(output, command, null, null);
                }
            }
        } else if (target != null) {
            handleSinglePointGet(command, output, LocalDateTime.parse(target));
        } else {
            LocalDateTime start = LocalDateTime.parse(command.getParameters().getStart());
            LocalDateTime end = LocalDateTime.parse(command.getParameters().getEnd());

            handleRangeGet(command, output, start, end);
        }
    }

    private ObjectNode findRangeInterval(final ArrayNode output, final String dataType,
                                         final LocalDate date, final int intervalIndex) {

        ObjectNode interval = output.objectNode();

        if (dataType.equals("DAM_PRICE")) {
            DamPricesPoints price = repository.findDamPrices(date, intervalIndex);
            if (price == null) {
                return null;
            }
            interval.put("priceAmount", price.getPriceAmount());

        } else if (dataType.equals("DAM_PRICE_PREDICTION")) {
            DamPricesPredictionPoints prediction =
                    repository.findDamPricesPrediction(date, intervalIndex);
            if (prediction == null) {
                return null;
            }
            interval.put("predictedPriceEurMwh", prediction.getPredictedPriceEurMwh());
            interval.put("spikeProbability", prediction.getSpikeProbability());

        } else if (dataType.equals("GRID_LOAD")) {
            GridLoadPoints load = repository.findGridLoad(date, intervalIndex);
            if (load == null) {
                return null;
            }
            interval.put("quantity", load.getQuantity());

        } else if (dataType.equals("GRID_LOAD_PREDICTION")) {
            GridLoadPredictionsPoints prediction =
                    repository.findGridLoadPrediction(date, intervalIndex);
            if (prediction == null) {
                return null;
            }
            interval.put("quantity", prediction.getQuantity());

        } else if (dataType.equals("IMBALANCE_PRICE")) {
            ImbalacePricesPoints price = repository.findImbalancePrices(date, intervalIndex);
            if (price == null) {
                return null;
            }
            interval.put("positiveImbalancePrice", price.getPositiveImbalancePrice());
            interval.put("negativeImbalancePrice", price.getNegativeImbalancePrice());

        } else if (dataType.equals("IMBALANCE_VOLUME")) {
            ImbalanceVolumesPoints volume = repository.findImbalanceVolumes(date, intervalIndex);
            if (volume == null) {
                return null;
            }
            interval.put("positiveImbalanceVol", volume.getPositiveImbalanceVol());
            interval.put("negativeImbalanceVol", volume.getNegativeImbalanceVol());

        } else {
            throw new IllegalArgumentException("Unknown data type: " + dataType);
        }

        return interval;
    }

    private void handleRangeGet(final CommandInput command, final ArrayNode output,
                                final LocalDateTime start, final LocalDateTime end) {
        String dataType = command.getParameters().getDataType();
        String clientTier = command.getParameters().getClientTier();


        if (dataType.equals("DAM_PRICE_PREDICTION") && clientTier.equals("FREE")) {
            addGetAccessError(output, command, start, end);
            return;
        }

        ArrayNode days = output.arrayNode();

        LocalDate currentDate = null;
        ArrayNode intervals = null;

        LocalDateTime time = start;
        while (time.isBefore(end)) {
            LocalDate date = time.toLocalDate();

            int minutes = time.getHour() * MINUTES_PER_HOUR + time.getMinute();
            int intervalIndex = minutes / INTERVAL_MINUTES + 1;

            ObjectNode interval = findRangeInterval(output, dataType, date, intervalIndex);

            if (interval != null) {
                if (currentDate == null || !date.equals(currentDate)) {
                    ObjectNode day = days.addObject();
                    day.put("id", dataType);
                    day.put("date", date.toString());
                    intervals = day.putArray("data");
                    currentDate = date;
                }

                String intervalText = time.toLocalTime() + " - "
                        + time.plusMinutes(INTERVAL_MINUTES).toLocalTime();
                interval.put("interval", intervalText);
                interval.put("intervalIndex", intervalIndex);

                intervals.add(interval);
            }

            time = time.plusMinutes(INTERVAL_MINUTES);
        }

        if (days.isEmpty()) {
            addGetWarning(output, command, start, end);
            return;
        }


        ObjectNode response = output.addObject();
        response.put("command", command.getCommand());
        response.put("timestamp", command.getTimestamp());
        response.put("status", "SUCCESS");
        response.put("from", start.toString());
        response.put("to", end.toString());
        response.set("data", days);
    }

    private void addGetWarning(final ArrayNode output, final CommandInput command,
                               final LocalDateTime from, final LocalDateTime to) {
        ObjectNode warning = addWarning(
                output, command, "Data is not yet available. Try again later.");

        warning.put("from", from == null ? null : from.toString());
        warning.put("to", to == null ? null : to.toString());
        warning.putNull("data");
    }

    private ObjectNode addSinglePointSuccess(final ArrayNode output, final CommandInput command,
                                             final String dataType, final LocalDateTime from,
                                             final LocalDateTime to, final int intervalIndex) {
        ObjectNode response = output.addObject();
        response.put("command", command.getCommand());
        response.put("timestamp", command.getTimestamp());
        response.put("status", "SUCCESS");
        response.put("from", from.toString());
        response.put("to", to.toString());

        ArrayNode days = response.putArray("data");
        ObjectNode day = days.addObject();
        day.put("id", dataType);
        day.put("date", from.toLocalDate().toString());

        ArrayNode intervals = day.putArray("data");
        ObjectNode interval = intervals.addObject();
        interval.put("interval", from.toLocalTime() + " - " + to.toLocalTime());
        interval.put("intervalIndex", intervalIndex);

        return interval;
    }

    private void addGetAccessError(final ArrayNode output, final CommandInput command,
                                   final LocalDateTime from, final LocalDateTime to) {
        ObjectNode error = output.addObject();
        error.put("command", command.getCommand());
        error.put("timestamp", command.getTimestamp());
        error.put("from", from == null ? null : from.toString());
        error.put("to", to == null ? null : to.toString());
        error.put("status", "ERROR");
        error.put("message",
                "Access denied: DAM_PRICE_PREDICTION requires PREMIUM subscription.");
    }
}
