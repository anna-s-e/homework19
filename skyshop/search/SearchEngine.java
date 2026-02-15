package org.skypro.skyshop.search;

import java.util.*;

public class SearchEngine {
    private Searchable[] searchables;
    private int count;

    public SearchEngine(int size) {
        this.searchables = new Searchable[size];
        this.count = 0;
    }

    public void add(Searchable searchable) {
        if (count < searchables.length) {
            searchables[count] = searchable;
            count++;
        } else {
            System.out.println("Невозможно добавить элемент: хранилище переполнено");
        }
    }

    public int getCount() {
        return count;
    }

    public Map<String, Searchable> search(String searchTerm) {

        Map<String, Searchable> resultMap = new TreeMap<>();

        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        for (int i = 0; i < count; i++) {
            Searchable searchable = searchables[i];

            if (searchable.getSearchTerm().toLowerCase().contains(lowerCaseSearchTerm)) {
                resultMap.put(searchable.getName(), searchable);
            }
        }

        return resultMap;
    }

    public Searchable findBestMatch(String searchTerm) throws BestResultNotFound {
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new BestResultNotFound("Поисковый запрос не может быть пустым");
        }

        Searchable bestMatch = null;
        int maxOccurrences = 0;
        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        for (int i = 0; i < count; i++) {
            Searchable current = searchables[i];
            String content = current.getSearchTerm().toLowerCase();

            int occurrences = countOccurrences(content, lowerCaseSearchTerm);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = current;
            }
        }

        if (bestMatch == null) {
            throw new BestResultNotFound("Не найден подходящий результат для запроса: " + searchTerm);
        }

        return bestMatch;
    }

    private int countOccurrences(String text, String searchTerm) {
        if (searchTerm.isEmpty()) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(searchTerm, index)) != -1) {
            count++;
            index += searchTerm.length();
        }

        return count;
    }
}