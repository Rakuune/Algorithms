import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;


import oy.interact.tira.factories.HashTableFactory;
import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedContainer;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;

public class CodeWordsCounter {

	private TIRAKeyedContainer<String, Integer> codeWords;

	public long cumulativeTimeInMilliseconds;

	private static final int MAX_WORD_SIZE = 4096;

	private String[] allWordsArray;
	private int wordsCount;

	public CodeWordsCounter() {
		codeWords = HashTableFactory.createHashTable();
		allWordsArray = new String[10];
		wordsCount = 0;
	}

	public void countWordsinSourceCodeFiles(File inDirectory) throws IOException {

		if (null == codeWords) {
			System.out.println("No implementation for hashtable, doing nothing.");
			return;
		}
		cumulativeTimeInMilliseconds = 0;
		System.out.println("Started counting, please wait...");

		Files.walkFileTree(inDirectory.toPath(), new SimpleFileVisitor<Path>() {
			PathMatcher matcher = FileSystems.getDefault()
					.getPathMatcher("glob:*.{c,h,cc,cpp,hpp,java,swift,py,html,css,js}");

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				if (file != null && matcher.matches(file.getFileName())) {
					try {
						countWordsFrom(file.toFile());
					} catch (OutOfMemoryError | IOException e) {
						e.printStackTrace();
						return FileVisitResult.TERMINATE;
					}
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException e) {
				return FileVisitResult.CONTINUE;
			}

		});
	}

	public void countWordsFrom(File file) throws IOException {
		long start = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int ch;
		StringBuilder word = new StringBuilder();
		while ((ch = reader.read()) != -1) {
			if (Character.isLetterOrDigit((char) ch)) {
				word.append((char) ch);
			} else if (word.length() > 0) {
				processWord(word.toString());
				word.setLength(0);
			}
		}
		if (word.length() > 0) {
			processWord(word.toString());
		}
		reader.close();

		cumulativeTimeInMilliseconds += System.currentTimeMillis() - start;
	}

	private void processWord(String word) {
		String foundWord = word.toLowerCase();
		Integer count = codeWords.get(foundWord);
		if (count == null) {
			codeWords.add(foundWord, 1);
		} else {
			codeWords.add(foundWord, count + 1);
		}
	}


	@SuppressWarnings("unchecked")
	public Pair<String, Integer>[] topCodeWords(int topCount) throws Exception {
        Pair<String, Integer>[] allWords = new Pair[wordsCount];
        for (int i = 0; i < wordsCount; i++) {
            String word = allWordsArray[i];
            allWords[i] = new Pair<>(word, codeWords.get(word));
        }

        Algorithms.fastSort(allWords, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2) {
                return p2.getValue().compareTo(p1.getValue());
            }
        });

        int resultSize = Math.min(topCount, allWords.length);
        Pair<String, Integer>[] topWords = new Pair[resultSize];
        for (int i = 0; i < resultSize; i++) {
            topWords[i] = allWords[i];
        }

        return topWords;
	}
}
