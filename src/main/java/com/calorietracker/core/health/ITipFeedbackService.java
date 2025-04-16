package com.calorietracker.core.health;

/**
 * Interface for handling tip feedback, separated from IHealthTipService to follow
 * Interface Segregation Principle.
 */
public interface ITipFeedbackService {
    /**
     * Updates the tip generation algorithm based on user feedback.
     * @param tipId ID of the tip receiving feedback
     * @param feedback User feedback on the tip's relevance
     */
    void processTipFeedback(String tipId, TipFeedback feedback);

    /**
     * Retrieves the feedback history for a specific tip.
     * @param tipId ID of the tip
     * @return The feedback history for the tip
     */
    TipFeedbackHistory getFeedbackHistory(String tipId);

    /**
     * Analyzes feedback patterns to improve tip relevance.
     * @return Analysis of feedback patterns
     */
    FeedbackAnalysis analyzeFeedbackPatterns();
}