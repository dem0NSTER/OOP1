
import org.example.KmpSubstringSearcher;
import org.example.SubstringSearcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KmpSubstringSearcherTest {

    @TempDir
    Path tempDir;

    private final SubstringSearcher searcher = new KmpSubstringSearcher();

    @Test
    void shouldFindRussianSubstring() throws IOException {
        Path file = tempDir.resolve("input.txt");

        Files.writeString(
                file,
                "абракадабра",
                StandardCharsets.UTF_8
        );

        List<Long> result = searcher.find(file, "бра");

        assertEquals(List.of(1L, 8L), result);
    }

    @Test
    void shouldFindOverlappingSubstrings() throws IOException {
        Path file = tempDir.resolve("overlap.txt");

        Files.writeString(file, "abababac", StandardCharsets.UTF_8);

        assertEquals(
                List.of(0L, 2L),
                searcher.find(file, "ababa")
        );

        assertEquals(
                List.of(),
                searcher.find(file, "xyz")
        );
    }

    @Test
    void shouldHandleUnicodeAndBufferBoundary() throws IOException {
        Path file = tempDir.resolve("unicode.txt");

        String content = "x".repeat(8191) + "😀бра😀";
        Files.writeString(file, content, StandardCharsets.UTF_8);

        assertEquals(
                List.of(8191L, 8195L),
                searcher.find(file, "😀")
        );

        assertEquals(
                List.of(8192L),
                searcher.find(file, "бра")
        );

        assertEquals(
                List.of(8191L),
                searcher.find(file, "😀бра")
        );
    }
}
