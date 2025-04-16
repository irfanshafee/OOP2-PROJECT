package com.calorietracker.repository;

import com.calorietracker.core.storage.IFileStorage;
import com.calorietracker.core.storage.StorageException;
import com.calorietracker.model.MealEntry;
import com.calorietracker.core.meal.MealType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for handling meal log persistence in a structured text file.
 */
public class MealLogRepository {
    private static final String MEAL_LOG_FILE = "meal_logs.txt";
    private static final String DELIMITER = "|";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final IFileStorage fileStorage;

    public MealLogRepository(IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void saveMealEntry(MealEntry entry) {
        String logLine = formatMealEntry(entry);
        fileStorage.appendToFile(logLine + "\n", MEAL_LOG_FILE);
    }

    public List<MealEntry> getAllMealEntries() {
        List<String> lines = fileStorage.readAllLines(MEAL_LOG_FILE);
        List<MealEntry> entries = new ArrayList<>();
        
        for (String line : lines) {
            try {
                entries.add(parseMealEntry(line));
            } catch (Exception e) {
                throw new StorageException("Failed to parse meal entry: " + line, e);
            }
        }
        
        return entries;
    }

    private String formatMealEntry(MealEntry entry) {
        return String.join(DELIMITER,
            entry.getId(),
            entry.getDateTime().format(DATE_FORMATTER),
            entry.getFoodName(),
            String.valueOf(entry.getQuantity()),
            entry.getMealType().toString(),
            String.valueOf(entry.getCalories()),
            String.valueOf(entry.getProtein()),
            String.valueOf(entry.getCarbohydrates()),
            String.valueOf(entry.getFats())
        );
    }

    private MealEntry parseMealEntry(String line) {
        String[] parts = line.split("\\" + DELIMITER);
        if (parts.length != 9) {
            throw new StorageException("Invalid meal entry format");
        }

        return new MealEntry(
            parts[0], // id
            parts[2], // foodName
            Double.parseDouble(parts[3]), // quantity
            MealType.valueOf(parts[4]), // mealType
            LocalDateTime.parse(parts[1], DATE_FORMATTER), // dateTime
            Double.parseDouble(parts[5]), // calories
            Double.parseDouble(parts[6]), // protein
            Double.parseDouble(parts[7]), // carbohydrates
            Double.parseDouble(parts[8])  // fats
        );
    }

    public List<MealEntry> getMealEntriesByDate(LocalDateTime date) {
        return getAllMealEntries().stream()
            .filter(entry -> entry.getDateTime().toLocalDate().equals(date.toLocalDate()))
            .toList();
    }

    public Optional<MealEntry> getMealEntryById(String id) {
        return getAllMealEntries().stream()
            .filter(entry -> entry.getId().equals(id))
            .findFirst();
    }
}