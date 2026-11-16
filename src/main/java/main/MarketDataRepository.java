package main;

import entities.DamPrices;
import entities.DamPricesPrediction;
import entities.points.DamPricesPoints;
import entities.points.DamPricesPredictionPoints;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MarketDataRepository {
        private Map<LocalDate, DamPrices> damPricesByDate  = new HashMap<>();

        public void saveDamPrices(LocalDate date, DamPricesPoints points){
                DamPrices document = damPricesByDate.get(date);

                if(document == null){
                        document = new DamPrices("EUR", "MWH");
                        damPricesByDate.put(date, document);
                }
                document.setPoint(points.getIntervalIndex(), points);
        }

        public DamPricesPoints findDamPrices(LocalDate date, int intervalIndex){
                DamPrices document = damPricesByDate.get(date);

                if(document == null){
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, DamPricesPrediction> damPricesPredictionByDate = new HashMap<>();

        public void saveDamPricesPrediction(LocalDate date, DamPricesPredictionPoints point){
                DamPricesPrediction document = damPricesPredictionByDate.get(date);

                if(document == null){
                        document = new DamPricesPrediction("EUR", "MWH");
                        damPricesPredictionByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        public DamPricesPredictionPoints findDamPricesPrediction(LocalDate date, int intervalIndex){
                DamPricesPrediction document = damPricesPredictionByDate.get(date);

                if(document == null){
                        return null;
                }

                return document.getPoint(intervalIndex);
        }



}
