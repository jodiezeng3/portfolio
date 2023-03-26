/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class DecayingLearningRateSchedule implements LearningRateSchedule{

    @Override
    public double alpha(int t) {
        return 1000.0/(1000.0 + t);
    }
}
