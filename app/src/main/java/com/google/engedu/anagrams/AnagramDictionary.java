/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import android.util.Log;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet wordSet = new HashSet();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String key = sortLetters(word);
            if (lettersToWord.containsKey(key)) {
                lettersToWord.get(key).add(word);
            }
            else {
                ArrayList<String> starter = new ArrayList<>();
                starter.add(word);
                lettersToWord.put(key, starter);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String word : wordList) {
            if (word.length() == targetWord.length()) {
                if (sortLetters(targetWord).equals(sortLetters(word))) {
                    result.add(word);
                }
            }
        }
        return result;
    }

    public String sortLetters(String toSort) {
        // Sort method found here: https://www.geeksforgeeks.org/sort-a-string-in-java-2-different-ways/
        char temp[] = toSort.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String addedLetter;
        for (char i = 'a'; i <= 'z'; i++) { // cycles through ASCII table
            addedLetter = word + String.valueOf(i);
            addedLetter = sortLetters(addedLetter);

            if(lettersToWord.containsKey(addedLetter)) {
                // looks in lettersToWord (hashMap)
                // for each letter of the alphabet, return all of those valid words
                result.addAll(lettersToWord.get(addedLetter));
            }
        }
        for (String toPrint : result)
            Log.d("result", toPrint);
        return result;
    }

    public String pickGoodStarterWord() {
        return "rots";
    }
}
