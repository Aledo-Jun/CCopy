package com.ccopy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StandardHeaders {
    public static final Set<String> SYSTEM_HEADERS = new HashSet<>(Arrays.asList(
            // C 헤더
            "cassert", "cctype", "cerrno", "cfloat", "ciso646", "climits", "clocale", "cmath",
            "csetjmp", "csignal", "cstdarg", "cstddef", "cstdio", "cstdlib", "cstring", "ctime",
            "cwchar", "cwctype",

            // C++11 이전에 포함되지 않은 경우 제외되거나, 조건부 포함 헤더
            "ccomplex", "cfenv", "cinttypes", "cstdalign", "cstdbool", "cstdint", "ctgmath", "cuchar",

            // C++ 기본 헤더
            "algorithm", "bitset", "complex", "deque", "exception", "fstream", "functional", "iomanip",
            "ios", "iosfwd", "iostream", "istream", "iterator", "limits", "list", "locale", "map",
            "memory", "new", "numeric", "ostream", "queue", "set", "sstream", "stack", "stdexcept",
            "streambuf", "string", "typeinfo", "utility", "valarray", "vector",

            // C++11 이후 추가된 헤더
            "array", "atomic", "chrono", "codecvt", "condition_variable", "forward_list", "future",
            "initializer_list", "mutex", "random", "ratio", "regex", "scoped_allocator", "system_error",
            "thread", "tuple", "typeindex", "type_traits", "unordered_map", "unordered_set",

            // C++14 이후
            "shared_mutex",

            // C++17 이후
            "any", "charconv", "filesystem", "optional", "memory_resource", "string_view", "variant",

            // C++20 이후
            "barrier", "bit", "compare", "concepts", "coroutine", "latch", "numbers", "ranges", "span",
            "stop_token", "semaphore", "source_location", "syncstream", "version",

            // TR1 헤더
            "tr1/array", "tr1/cctype", "tr1/cfenv", "tr1/cfloat", "tr1/cinttypes", "tr1/climits",
            "tr1/cmath", "tr1/complex", "tr1/cstdarg", "tr1/cstdbool", "tr1/cstdint", "tr1/cstdio",
            "tr1/cstdlib", "tr1/ctgmath", "tr1/ctime", "tr1/cwchar", "tr1/cwctype", "tr1/functional",
            "tr1/random", "tr1/tuple", "tr1/unordered_map", "tr1/unordered_set", "tr1/utility"
    ));

    public static boolean is_std_header(String header) {
        return SYSTEM_HEADERS.contains(header);
    }
}
