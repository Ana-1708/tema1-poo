package main;

import entities.DailyDocument;
import entities.DamPrices;
import entities.DamPricesPrediction;
import entities.GridLoad;
import entities.GridLoadPrediction;
import entities.ImbalancePrices;
import entities.ImbalanceVolumes;
import entities.points.DamPricesPoints;
import entities.points.DamPricesPredictionPoints;
import entities.points.GridLoadPoints;
import entities.points.GridLoadPredictionsPoints;
import entities.points.ImbalacePricesPoints;
import entities.points.ImbalanceVolumesPoints;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class MarketDataRepository {
        private static final int INTERVALS_PER_DAY = 96;
        private static final int INTERVAL_MINUTES = 15;

        private Map<LocalDate, DamPrices> damPricesByDate  = new HashMap<>();

        /**
         * Saves a DAM price point for the given date.
         *
         * @param date the date of the point
         * @param points the point to save
         */
        public void saveDamPrices(final LocalDate date, final DamPricesPoints points) {
                DamPrices document = damPricesByDate.get(date);

                if (document == null) {
                        document = new DamPrices("EUR", "MWH");
                        damPricesByDate.put(date, document);
                }
                document.setPoint(points.getIntervalIndex(), points);
        }

        /**
         * Finds a DAM price point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public DamPricesPoints findDamPrices(final LocalDate date, final int intervalIndex) {
                DamPrices document = damPricesByDate.get(date);

                if (document == null) {
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, DamPricesPrediction> damPricesPredictionByDate = new HashMap<>();

        /**
         * Saves a DAM price prediction point for the given date.
         *
         * @param date the date of the point
         * @param point the point to save
         */
        public void saveDamPricesPrediction(final LocalDate date,
                                            final DamPricesPredictionPoints point) {
                DamPricesPrediction document = damPricesPredictionByDate.get(date);

                if (document == null) {
                        document = new DamPricesPrediction("EUR", "MWH");
                        damPricesPredictionByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        /**
         * Finds a DAM price prediction point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public DamPricesPredictionPoints findDamPricesPrediction(final LocalDate date,
                                                                 final int intervalIndex) {
                DamPricesPrediction document = damPricesPredictionByDate.get(date);

                if (document == null) {
                        return null;
                }

                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, GridLoadPrediction> gridLoadPredictionByDate = new HashMap<>();

        /**
         * Saves a grid load prediction point for the given date.
         *
         * @param date the date of the point
         * @param point the point to save
         */
        public void saveGridLoadPrediction(final LocalDate date,
                                           final GridLoadPredictionsPoints point) {
                GridLoadPrediction document = gridLoadPredictionByDate.get(date);
                if (document == null) {
                        document = new GridLoadPrediction("MWH");
                        gridLoadPredictionByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        /**
         * Finds a grid load prediction point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public GridLoadPredictionsPoints findGridLoadPrediction(final LocalDate date,
                                                                final int intervalIndex) {
                GridLoadPrediction document = gridLoadPredictionByDate.get(date);

                if (document == null) {
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, ImbalancePrices> imbalancePricesByDate = new HashMap<>();

        /**
         * Saves an imbalance price point for the given date.
         *
         * @param date the date of the point
         * @param point the point to save
         */
        public void saveImbalancePrices(final LocalDate date, final ImbalacePricesPoints point) {
                ImbalancePrices document = imbalancePricesByDate.get(date);

                if (document == null) {
                        document = new ImbalancePrices("EUR", "MWH");
                        imbalancePricesByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        /**
         * Finds an imbalance price point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public ImbalacePricesPoints findImbalancePrices(final LocalDate date,
                                                        final int intervalIndex) {
                ImbalancePrices document = imbalancePricesByDate.get(date);
                if (document == null) {
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, ImbalanceVolumes> imbalanceVolumesByDate = new HashMap<>();

        /**
         * Saves an imbalance volume point for the given date.
         *
         * @param date the date of the point
         * @param point the point to save
         */
        public void saveImbalanceVolumes(final LocalDate date, final ImbalanceVolumesPoints point) {
                ImbalanceVolumes document = imbalanceVolumesByDate.get(date);
                if (document == null) {
                        document = new ImbalanceVolumes("MWH");
                        imbalanceVolumesByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        /**
         * Finds an imbalance volume point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public ImbalanceVolumesPoints findImbalanceVolumes(final LocalDate date,
                                                           final int intervalIndex) {
                ImbalanceVolumes document = imbalanceVolumesByDate.get(date);
                if (document == null) {
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        private Map<LocalDate, GridLoad> gridLoadByDate = new HashMap<>();

        /**
         * Saves a grid load point for the given date.
         *
         * @param date the date of the point
         * @param point the point to save
         */
        public void saveGridLoad(final LocalDate date, final GridLoadPoints point) {
                GridLoad document = gridLoadByDate.get(date);
                if (document == null) {
                        document = new GridLoad("MWH");
                        gridLoadByDate.put(date, document);
                }
                document.setPoint(point.getIntervalIndex(), point);
        }

        /**
         * Finds a grid load point.
         *
         * @param date the date of the point
         * @param intervalIndex the index of the interval
         * @return the point, or null if it does not exist
         */
        public GridLoadPoints findGridLoad(final LocalDate date, final int intervalIndex) {
                GridLoad document = gridLoadByDate.get(date);
                if (document == null) {
                        return null;
                }
                return document.getPoint(intervalIndex);
        }

        /**
         * Finds the grid load points before the given interval, closest first.
         *
         * @param date the date of the points
         * @param intervalIndex the index of the current interval
         * @return the previous points, or an empty list if there are none
         */
        public List<GridLoadPoints> findFirstValidGridLoadPoint(final LocalDate date,
                                                                final int intervalIndex) {
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

        /**
         * Finds the start of the latest interval that has data for the given type.
         *
         * @param dataType the type of data
         * @return the start of the latest interval, or null if there is no data
         */
        public LocalDateTime findLatestIntervalStart(final String dataType) {
                Map<LocalDate, ? extends DailyDocument<?>> documentsByDate;

                if ("DAM_PRICE".equals(dataType)) {
                        documentsByDate = damPricesByDate;
                } else if ("DAM_PRICE_PREDICTION".equals(dataType)) {
                        documentsByDate = damPricesPredictionByDate;
                } else if ("GRID_LOAD".equals(dataType)) {
                        documentsByDate = gridLoadByDate;
                } else if ("GRID_LOAD_PREDICTION".equals(dataType)) {
                        documentsByDate = gridLoadPredictionByDate;
                } else if ("IMBALANCE_PRICE".equals(dataType)) {
                        documentsByDate = imbalancePricesByDate;
                } else if ("IMBALANCE_VOLUME".equals(dataType)) {
                        documentsByDate = imbalanceVolumesByDate;
                } else {
                        throw new IllegalArgumentException("Unknown data type: " + dataType);
                }

                List<LocalDate> dates = new ArrayList<>(documentsByDate.keySet());
                Collections.sort(dates);
                Collections.reverse(dates);

                for (LocalDate date : dates) {
                        DailyDocument<?> document = documentsByDate.get(date);

                        for (int i = INTERVALS_PER_DAY; i >= 1; i--) {
                                if (document.getPoint(i) != null) {
                                        int minutes = (i - 1) * INTERVAL_MINUTES;
                                        return date.atStartOfDay().plusMinutes(minutes);
                                }
                        }
                }

                return null;
        }

        public void clearPoint (final String dataType, final LocalDate date, final int intervalIndex) {
               DailyDocument<?> document;

               if("GRID_LOAD".equals(dataType)) {
                   document = gridLoadByDate.get(date);
               } else if ("GRID_LOAD_PREDICTION".equals(dataType)) {
                   document = gridLoadPredictionByDate.get(date);
               } else if ("DAM_PRICE".equals(dataType)) {
                   document = damPricesByDate.get(date);
               } else if ("DAM_PRICE_PREDICTION".equals(dataType)) {
                   document = damPricesPredictionByDate.get(date);
               } else if ("IMBALANCE_PRICE".equals(dataType)) {
                   document = imbalancePricesByDate.get(date);
               } else if ("IMBALANCE_VOLUME".equals(dataType)) {
                   document = imbalanceVolumesByDate.get(date);
               } else {
                   throw new IllegalArgumentException("Unknown data type: " + dataType);
               }

               if(document != null){
                     document.setPoint(intervalIndex, null);
               }
        }
}
