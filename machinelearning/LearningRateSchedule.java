/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jzeng
 */
public interface LearningRateSchedule {
    /**
     * return the learning rate alpha for the given iteration t
     */
    public double alpha(int t);
}
