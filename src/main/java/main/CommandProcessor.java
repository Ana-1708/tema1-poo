package main;

import entities.DamPrices;
import entities.points.DamPricesPoints;
import fileio.CommandInput;
import fileio.MarketDataInput;

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
                }
            }
        }
    }


