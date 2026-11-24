package main;

import entities.*;
import entities.points.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

         private Map<LocalDate, GridLoadPrediction> gridLoadPredictionByDate = new HashMap<>();

        public void saveGridLoadPrediction(LocalDate date, GridLoadPredictionsPoints point){
                GridLoadPrediction document = gridLoadPredictionByDate.get(date);
                 if(document == null){
                         document = new GridLoadPrediction("MWH");
                         gridLoadPredictionByDate.put(date, document);
                 }
                 document.setPoint(point.getIntervalIndex(), point);
        }

        public GridLoadPredictionsPoints findGridLoadPrediction(LocalDate date, int intervalIndex){
                GridLoadPrediction document = gridLoadPredictionByDate.get(date);

                if(document == null){
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, ImbalancePrices> imbalancePricesByDate = new HashMap<>();

        public void saveImbalancePrices(LocalDate date, ImbalacePricesPoints point){
                ImbalancePrices document = imbalancePricesByDate.get(date);

                if(document == null){
                        document = new ImbalancePrices("EUR", "MWH");
                        imbalancePricesByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        public ImbalacePricesPoints findImbalancePrices(LocalDate date, int intervalIndex){
                ImbalancePrices document = imbalancePricesByDate.get(date);
                if(document == null){
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, ImbalanceVolumes> imbalanceVolumesByDate = new HashMap<>();

        public void saveImbalanceVolumes(LocalDate date, ImbalanceVolumesPoints point){
                ImbalanceVolumes document = imbalanceVolumesByDate.get(date);
                if(document == null){
                        document = new ImbalanceVolumes("MWH");
                        imbalanceVolumesByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        public ImbalanceVolumesPoints findImbalanceVolumes(LocalDate date, int intervalIndex){
                ImbalanceVolumes document = imbalanceVolumesByDate.get(date);
                if(document == null){
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, GridLoad> gridLoadByDate = new HashMap<>();

        public void saveGridLoad(LocalDate date, GridLoadPoints point){
                GridLoad document = gridLoadByDate.get(date);
                if(document == null){
                        document = new GridLoad("MWH");
                        gridLoadByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        public GridLoadPoints findGridLoad(LocalDate date, int intervalIndex){
                GridLoad document = gridLoadByDate.get(date);
                if(document == null){
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        public List<GridLoadPoints> findFirstValidGridLoadPoint(LocalDate date, int intervalIndex) {
                List<GridLoadPoints> previousPoints = new ArrayList<>();
                GridLoad document = gridLoadByDate.get(date);

                if (document == null) {
                        return previousPoints;
                }

                for (int interval = intervalIndex - 1; interval >= 1; interval--) {
                        GridLoadPoints previousPoint = document.getPoint(interval);
                        if (previousPoint != null) {
                                previousPoints.add(previousPoint);
                        }
                }

                return previousPoints;
        }
}
