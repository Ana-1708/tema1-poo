package main;

import entities.*;
import entities.points.*;
import fileio.CommandInput;
import fileio.MarketDataInput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommandProcessor {
    private final DataValidator validator;
    private final MarketDataRepository repository;
    private boolean importActive = false;

    public CommandProcessor(DataValidator validator, MarketDataRepository repository){
        this.repository = repository;
        this.validator = validator;
    }

    public void process(CommandInput command, ArrayNode output){
        if("startImport".equals(command.getCommand())){
            importActive = true;
        }else if("stopImport".equals(command.getCommand())){
            importActive = false;
        }else if("import".equals(command.getCommand()) && importActive){
            handleImportAndPatch(command, output);
        }else if("patch".equals(command.getCommand())){
           handleImportAndPatch(command, output);
        }
    }

    private void addImportWarning(ArrayNode output, CommandInput command, MarketDataInput point, String dataType, int intervalIndex, String message) {
        ObjectNode warning = output.addObject();
        warning.put("command", command.getCommand());
        warning.put("timestamp", command.getTimestamp());
        warning.put("status", "WARNING");
        warning.put("id", dataType);
        warning.put("date", point.date());
        warning.put("interval", point.interval().replaceAll("\\s*-\\s*", " - "));
        warning.put("intervalIndex", intervalIndex);
        warning.put("message", message);
    }

    public void handleImportAndPatch (CommandInput command, ArrayNode output){
        List<MarketDataInput> importedPoints = command.getParameters().getData();
        boolean isPatch = "patch".equals(command.getCommand());
        for (MarketDataInput point : importedPoints){
            String dataType;
            if(isPatch){
                dataType = command.getParameters().getDataType();
            }else{
                dataType = point.id();
            }
            LocalDate date = LocalDate.parse(point.date());
            String startText = point.interval().split("-", 2)[0].trim();
            LocalTime start = LocalTime.parse(startText);
            int minutes = start.getHour() * 60 + start.getMinute();
            int intervalIndex = minutes / 15 + 1;

            String message;

            if("DAM_PRICE".equals(dataType)){
                Double priceAmount = point.priceAmount();
                message = validator.validateDamPrice(priceAmount);
                if(message == null){
                    repository.saveDamPrices(date, new DamPricesPoints(intervalIndex, priceAmount));
                }
                } else if("DAM_PRICE_PREDICTION".equals(dataType)){
                    Double spikeProbability = point.spikeProbability();
                    Double predictedPriceEurMwh = point.predictedPriceEurMwh();
                    message = validator.validateDamPricesPrediction(predictedPriceEurMwh, spikeProbability);
                if (message == null) {
                    repository.saveDamPricesPrediction(date, new DamPricesPredictionPoints(intervalIndex, predictedPriceEurMwh, spikeProbability));
                }
            } else if("GRID_LOAD".equals(dataType)){

                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity == null ? null : quantity.doubleValue();;

                List<GridLoadPoints> previousPoints = repository.findFirstValidGridLoadPoint(date, intervalIndex);
                Double previousQuantity = previousPoints.isEmpty() ? null : previousPoints.get(0).getQuantity();
                message = validator.validateGridLoad(quantityDouble, previousQuantity);
                if (message == null) {
                    repository.saveGridLoad(date, new GridLoadPoints(quantityDouble, intervalIndex, point.reservesRequiredMW()));
                }
            } else if("GRID_LOAD_PREDICTION".equals(dataType)){
                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity == null ? null : quantity.doubleValue();;

                message = validator.validateGridLoadPrediction(quantityDouble, reservesRequiredMW);
                if (message == null) {
                    GridLoadPredictionsPoints savedPoint = new GridLoadPredictionsPoints(intervalIndex, quantityDouble, reservesRequiredMW);
                    repository.saveGridLoadPrediction(date, savedPoint);
                }
            } else if("IMBALANCE_PRICE".equals(dataType)){
                Double positiveImbalancePrice = point.positiveImbalancePrice();
                Double negativeImbalancePrice = point.negativeImbalancePrice();
                message = validator.validateImbalancePrices(positiveImbalancePrice, negativeImbalancePrice);
                if(message == null){
                    ImbalacePricesPoints savedPoint = new ImbalacePricesPoints(intervalIndex, positiveImbalancePrice, negativeImbalancePrice);
                    repository.saveImbalancePrices(date, savedPoint);
                }
            } else if("IMBALANCE_VOLUME".equals(dataType)){
                Double positiveImbalanceVolumes = point.positiveImbalanceVol();
                Double negativeImbalanceVolumes = point.negativeImbalanceVol();
                message = validator.validateImbalanceVolumes(positiveImbalanceVolumes, negativeImbalanceVolumes);
                if(message == null){
                    ImbalanceVolumesPoints savedPoint = new ImbalanceVolumesPoints(intervalIndex, positiveImbalanceVolumes, negativeImbalanceVolumes);
                    repository.saveImbalanceVolumes(date, savedPoint);
                }
            } else {
                continue;
            }
            if(message!=null){
                addImportWarning(output, command, point, dataType, intervalIndex, message);
            }
            }
        }




    }


