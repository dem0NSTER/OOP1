package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface SubstringSearcher {
    List<Long> find(Path file, String pattern) throws IOException;
}
