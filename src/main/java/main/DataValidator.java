package main;

public final class DataValidator {
    private static final int MIN_DAM_PRICE = -500;
    private static final int MAX_DAM_PRICE = 4000;
    private static final int MAX_IMBALANCE_PRICE = 15_000;
    private static final int MAX_GRID_LOAD_CHANGE = 10_000;

    /**
     * Validates a DAM price value.
     *
     * @param priceAmount the price to check
     * @return the warning message, or null if the value is valid
     */
    public String validateDamPrice(final Double priceAmount) {
        if (priceAmount == null) {
            return "PriceAmount is null; interval skipped.";
        }

        if (priceAmount < MIN_DAM_PRICE || priceAmount > MAX_DAM_PRICE) {
            return "PriceAmount " + priceAmount
                    + " is outside the market range [-500, 4000] EUR/MWh; interval skipped.";
        }

        return null;
    }

    /**
     * Validates a DAM price prediction.
     *
     * @param predictedPriceEurMwh the predicted price to check
     * @param spikeProbability the spike probability to check
     * @return the warning message, or null if the values are valid
     */
    public String validateDamPricesPrediction(final Double predictedPriceEurMwh,
                                              final Double spikeProbability) {
        if (predictedPriceEurMwh == null) {
            return "PredictedPriceEurMwh is null; interval skipped.";
        }

        if (spikeProbability == null) {
            return "SpikeProbability is null; interval skipped.";
        }

        if (predictedPriceEurMwh < MIN_DAM_PRICE || predictedPriceEurMwh > MAX_DAM_PRICE) {
            return "PredictedPriceEurMwh " + predictedPriceEurMwh
                    + " is outside the market range [-500, 4000] EUR/MWh; interval skipped.";
        }

        if (spikeProbability < 0 || spikeProbability > 1) {
            return "SpikeProbability " + spikeProbability
                    + " is outside the valid range [0.0, 1.0]; interval skipped.";
        }

        return null;
    }

    /**
     * Validates a grid load prediction.
     *
     * @param quantity the predicted quantity to check
     * @param reservesRequiredMW the required reserves to check
     * @return the warning message, or null if the values are valid
     */
    public String validateGridLoadPrediction(final Double quantity,
                                             final Double reservesRequiredMW) {
        if (quantity == null) {
            return "Quantity is null; interval skipped.";
        }

        if (reservesRequiredMW == null) {
            return "ReservesRequiredMW is null; interval skipped.";
        }

        if (quantity < 0) {
            return "Quantity " + quantity
                    + " is below the minimum allowed value 0; interval skipped.";
        }

        if (reservesRequiredMW <= 0) {
            return "ReservesRequiredMW " + reservesRequiredMW
                    + " is not positive; interval skipped.";
        }

        return null;
    }

    /**
     * Validates imbalance prices.
     *
     * @param positiveImbalancePrice the positive imbalance price to check
     * @param negativeImbalancePrice the negative imbalance price to check
     * @return the warning message, or null if the values are valid
     */
    public String validateImbalancePrices(final Double positiveImbalancePrice,
                                          final Double negativeImbalancePrice) {
        if (positiveImbalancePrice == null) {
            return "PositiveImbalancePrice is null; interval skipped.";
        }

        if (negativeImbalancePrice == null) {
            return "NegativeImbalancePrice is null; interval skipped.";
        }

        if (positiveImbalancePrice < -MAX_IMBALANCE_PRICE
                || positiveImbalancePrice > MAX_IMBALANCE_PRICE) {
            return "PositiveImbalancePrice " + positiveImbalancePrice
                    + " exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.";
        }

        if (negativeImbalancePrice < -MAX_IMBALANCE_PRICE
                || negativeImbalancePrice > MAX_IMBALANCE_PRICE) {
            return "NegativeImbalancePrice " + negativeImbalancePrice
                    + " exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.";
        }

        return null;
    }

    /**
     * Validates imbalance volumes.
     *
     * @param positiveImbalanceVol the positive imbalance volume to check
     * @param negativeImbalanceVol the negative imbalance volume to check
     * @return the warning message, or null if the values are valid
     */
    public String validateImbalanceVolumes(final Double positiveImbalanceVol,
                                           final Double negativeImbalanceVol) {
        if (positiveImbalanceVol == null) {
            return "PositiveImbalanceVol is null; interval skipped.";
        }

        if (negativeImbalanceVol == null) {
            return "NegativeImbalanceVol is null; interval skipped.";
        }

        if (positiveImbalanceVol < 0) {
            return "PositiveImbalanceVol " + positiveImbalanceVol
                    + " is below the minimum allowed value 0; interval skipped.";
        }

        if (negativeImbalanceVol < 0) {
            return "NegativeImbalanceVol " + negativeImbalanceVol
                    + " is below the minimum allowed value 0; interval skipped.";
        }

        return null;
    }

    /**
     * Validates a grid load value against the previous interval.
     *
     * @param quantity the quantity to check
     * @param previousQuantity the quantity of the previous interval, or null
     * @return the warning message, or null if the value is valid
     */
    public String validateGridLoad(final Double quantity, final Double previousQuantity) {
        if (quantity == null) {
            return "Quantity is null; interval skipped.";
        }

        if (quantity < 0) {
            return "Quantity " + quantity
                    + " is below the minimum allowed value 0; interval skipped.";
        }

        if (previousQuantity != null
                && Math.abs(quantity - previousQuantity) > MAX_GRID_LOAD_CHANGE) {
            return "Quantity change exceeds 10000 MW compared with the previous interval;"
                    + " interval skipped.";
        }

        return null;
    }
}
