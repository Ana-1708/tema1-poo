package main;

import entities.*;
import entities.points.*;
import fileio.CommandInput;
import fileio.MarketDataInput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CommandProcessor {
    private final DataValidator validator;
    private final MarketDataRepository repository;
    private boolean importActive = false;

    public CommandProcessor(DataValidator validator, MarketDataRepository repository){
        this.repository = repository;
        this.validator = validator;
    }

    public void process(CommandInput command){
        if("startImport".equals(command.getCommand())){
            importActive = true;
        }else if("stopImport".equals(command.getCommand())){
            importActive = false;
        }else if("import".equals(command.getCommand()) && importActive){
            handleImport(command);
        }
    }

    public void handleImport (CommandInput command){
        List<MarketDataInput> importedPoints = command.getParameters().getData();
        for (MarketDataInput point : importedPoints){
            if("DAM_PRICE".equals(point.id())){
                Double priceAmount = point.priceAmount();
                String message = validator.validateDamPrice(priceAmount);
                    if (message == null) {
                        LocalDate date = LocalDate.parse(point.date());

                        String startText = point.interval().split("-", 2)[0].trim();
                        LocalTime start = LocalTime.parse(startText);
                        int minutes = start.getHour() * 60 + start.getMinute();
                        int intervalIndex = minutes / 15 + 1;

                        DamPricesPoints savedPoint = new DamPricesPoints(intervalIndex, priceAmount);
                        repository.saveDamPrices(date, savedPoint);
                    }
                } else if("DAM_PRICE_PREDICTION".equals(point.id())){
                    Double spikeProbability = point.spikeProbability();
                    Double predictedPriceEurMwh = point.predictedPriceEurMwh();
                    String message = validator.validateDamPricesPrediction(predictedPriceEurMwh, spikeProbability);
                if (message == null) {
                    LocalDate date = LocalDate.parse(point.date());

                    String startText = point.interval().split("-", 2)[0].trim();
                    LocalTime start = LocalTime.parse(startText);
                    int minutes = start.getHour() * 60 + start.getMinute();
                    int intervalIndex = minutes / 15 + 1;

                    DamPricesPredictionPoints savedPoint = new DamPricesPredictionPoints(intervalIndex, predictedPriceEurMwh, spikeProbability);
                    repository.saveDamPricesPrediction(date, savedPoint);
                }
            } else if("GRID_LOAD".equals(point.id())){

                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity.doubleValue();
                LocalDate date = LocalDate.parse(point.date());
                String startText = point.interval().split("-", 2)[0].trim();
                LocalTime start = LocalTime.parse(startText);
                int minutes = start.getHour() * 60 + start.getMinute();
                int intervalIndex = minutes / 15 + 1;

                List<GridLoadPoints> list = repository.findFirstValidGridLoadPoint(date, intervalIndex);
                for(GridLoadPoints p: list){
                    String message = validator.validateGridLoad(quantityDouble, p.getQuantity());
                    if(message == null){
                        GridLoadPoints savedPoint = new GridLoadPoints(quantityDouble, intervalIndex, reservesRequiredMW);
                        repository.saveGridLoad(date, savedPoint);
                        break;
                    }
                }
            } else if("GRID_LOAD_PREDICTION".equals(point.id())){
                Double reservesRequiredMW = point.reservesRequiredMW();
                BigDecimal quantity = point.quantity();
                Double quantityDouble = quantity.doubleValue();

                String message = validator.validateGridLoadPrediction(quantityDouble, reservesRequiredMW);
                if (message == null) {
                    LocalDate date = LocalDate.parse(point.date());

                    String startText = point.interval().split("-", 2)[0].trim();
                    LocalTime start = LocalTime.parse(startText);
                    int minutes = start.getHour() * 60 + start.getMinute();
                    int intervalIndex = minutes / 15 + 1;

                    GridLoadPredictionsPoints savedPoint = new GridLoadPredictionsPoints(intervalIndex, reservesRequiredMW, quantityDouble);
                    repository.saveGridLoadPrediction(date, savedPoint);
                }
            } else if("IMBALANCE_PRICE".equals(point.id())){
                Double positiveImbalancePrice = point.positiveImbalancePrice();
                Double negativeImbalancePrice = point.negativeImbalancePrice();
                String message = validator.validateImbalancePrices(positiveImbalancePrice, negativeImbalancePrice);
                if(message == null){
                    LocalDate date = LocalDate.parse(point.date());

                    String startText = point.interval().split("-", 2)[0].trim();
                    LocalTime start = LocalTime.parse(startText);
                    int minutes = start.getHour() * 60 + start.getMinute();
                    int intervalIndex = minutes / 15 + 1;

                    ImbalacePricesPoints savedPoint = new ImbalacePricesPoints(intervalIndex, positiveImbalancePrice, negativeImbalancePrice);
                    repository.saveImbalancePrices(date, savedPoint);
                }
            } else if("IMBALANCE_VOLUME".equals(point.id())){
                Double positiveImbalancePrice = point.positiveImbalancePrice();
                Double negativeImbalancePrice = point.negativeImbalancePrice();
                String message = validator.validateImbalancePrices(positiveImbalancePrice, negativeImbalancePrice);
                if(message == null){
                    LocalDate date = LocalDate.parse(point.date());

                    String startText = point.interval().split("-", 2)[0].trim();
                    LocalTime start = LocalTime.parse(startText);
                    int minutes = start.getHour() * 60 + start.getMinute();
                    int intervalIndex = minutes / 15 + 1;

                    ImbalanceVolumesPoints savedPoint = new ImbalanceVolumesPoints(intervalIndex, positiveImbalancePrice, negativeImbalancePrice);
                    repository.saveImbalanceVolumes(date, savedPoint);
                }
            }
            }
        }
    }


