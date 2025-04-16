package com.calorietracker.core.health;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for generating health tips and nutritional advice.
 * Follows Interface Segregation Principle by focusing only on health tip generation.
 */
public interface IHealthTipService {
    /**
     * Generates health tips based on daily nutritional data.
     * @param date The date to analyze
     * @return List of relevant health tips
     */
    List<HealthTip> generateDailyTips(LocalDateTime date);

    /**
     * Analyzes meal patterns over time.
     * @param startDate Start of the analysis period
     * @param endDate End of the analysis period
     * @return Analysis of meal patterns and trends
     */
    MealPatternAnalysis analyzeMealPatterns(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Gets general health tips from the rotating set.
     * @return List of general health tips
     */
    List<HealthTip> getGeneralTips();

    /**
     * Generates personalized recommendations based on nutritional history.
     * @param date The date to base recommendations on
     * @return List of personalized recommendations
     */
    List<NutritionRecommendation> getPersonalizedRecommendations(LocalDateTime date);

    /**
     * Updates the tip generation algorithm based on user feedback.
     * @param tipId ID of the tip receiving feedback
     * @param feedback User feedback on the tip's relevance
     */
    void processTipFeedback(String tipId, TipFeedback feedback);
}