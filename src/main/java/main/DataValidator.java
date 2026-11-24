package main;

import java.math.BigDecimal;

public class DataValidator {
    public String validateDamPrice(Double priceAmount) {
        if (priceAmount == null) {
            return "PriceAmount is null; interval skipped.";
        }

        if (priceAmount < -500 || priceAmount > 4000) {
            return "PriceAmount " + priceAmount
                    + " is outside the market range [-500, 4000] EUR/MWh; interval skipped.";
        }

        return null;
    }

    public String validateDamPricesPrediction(Double predictedPriceEurMwh,
                                              Double spikeProbability) {
        if (predictedPriceEurMwh == null) {
            return "PredictedPriceEurMwh is null; interval skipped.";
        }

        if (spikeProbability == null) {
            return "SpikeProbability is null; interval skipped.";
        }

        if (predictedPriceEurMwh < -500 || predictedPriceEurMwh > 4000) {
            return "PredictedPriceEurMwh " + predictedPriceEurMwh
                    + " is outside the market range [-500, 4000] EUR/MWh; interval skipped.";
        }

        if (spikeProbability < 0 || spikeProbability > 1) {
            return "SpikeProbability " + spikeProbability
                    + " is outside the valid range [0.0, 1.0]; interval skipped.";
        }

        return null;
    }

    public String validateGridLoadPrediction(Double quantity, Double reservesRequiredMW) {
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

    public String validateImbalancePrices(Double positiveImbalancePrice,
                                          Double negativeImbalancePrice) {
        if (positiveImbalancePrice == null) {
            return "PositiveImbalancePrice is null; interval skipped.";
        }

        if (negativeImbalancePrice == null) {
            return "NegativeImbalancePrice is null; interval skipped.";
        }

        if (positiveImbalancePrice < -15_000 || positiveImbalancePrice > 15_000) {
            return "PositiveImbalancePrice " + positiveImbalancePrice
                    + " exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.";
        }

        if (negativeImbalancePrice < -15_000 || negativeImbalancePrice > 15_000) {
            return "NegativeImbalancePrice " + negativeImbalancePrice
                    + " exceeds the maximum allowed value 15000 EUR/MWh; interval skipped.";
        }

        return null;
    }

    public String validateImbalanceVolumes(Double positiveImbalanceVol,
                                           Double negativeImbalanceVol) {
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

    public String validateGridLoad(Double quantity, Double previousQuantity) {
        if (quantity == null) {
            return "Quantity is null; interval skipped.";
        }

        if (quantity < 0) {
            return "Quantity " + quantity
                    + " is below the minimum allowed value 0; interval skipped.";
        }

        if (previousQuantity != null
                && Math.abs(quantity - previousQuantity) > 10_000) {
            return "Quantity change exceeds 10000 MW compared with the previous interval;"
                    + " interval skipped.";
        }

        return null;
    }
}