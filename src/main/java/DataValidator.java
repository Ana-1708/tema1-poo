public class DataValidator {
    public boolean validateDamPrice (Double priceAmount){
        if (priceAmount == null) {
            return false;
        }

        if(priceAmount < -500 || priceAmount > 4000){
            return false;
        }

        return true;
    }

    public boolean validateDamPricesPrediction (Double predictedPriceEurMwh, Double spikeProbability){

        if(predictedPriceEurMwh == null || spikeProbability == null){
            return false;
        }
        if(predictedPriceEurMwh < -500 || predictedPriceEurMwh > 4000){
            return false;
        }
        if(spikeProbability < 0 || spikeProbability > 1){
            return false;
        }
        return true;
    }

    public boolean validateGridLoadPrediction (Double quantity, Double reservesRequiredMW){
        if(quantity == null || reservesRequiredMW == null){
            return false;
        }

        if(quantity < 0 || reservesRequiredMW <= 0){
            return false;
        }

        return true;
    }

    public boolean validateImbalancePrices (Double positiveImbalancePrices, Double negativeImbalancePrices){
        if(positiveImbalancePrices == null || negativeImbalancePrices == null){
            return false;
        }
        if(positiveImbalancePrices < -15000 || positiveImbalancePrices > 15000){
            return false;
        }
        if(negativeImbalancePrices < -15000 || negativeImbalancePrices > 15000){
            return false;
        }
        return true;
    }

    public boolean validateImbalanceVolumes (Double positiveImbalancevol, Double negativeImbalancevol){
        if(positiveImbalancevol == null || negativeImbalancevol == null) {
            return false;
        }
        if(positiveImbalancevol < 0 || negativeImbalancevol < 0){
            return false;
        }
        return true;
    }

    public boolean validateGridLoad (Double quantity, Double previousQuantity){
        if(quantity == null || quantity < 0){
            return false;
        }
        boolean exceedsLimit = Math.abs(quantity - previousQuantity) > 10000;
        return exceedsLimit;
    }


}
