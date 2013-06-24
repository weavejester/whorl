# Whorl

Whorl is a library for generating unique fingerprints for Clojure data
structures. Equivalent data structures will always produce the same
fingerprint.

Only basic Clojure types are supported so far. Records and types will
produce unpredictable results. This library should be considered
experimental and in-progress.

## Installation

To install, add the following to your project `:dependencies`:

    [whorl "0.0.1"]

## Usage

Use the `fingerprint` function to generate a fingerprint for a data
structure.

```clojure
user=> (use 'whorl.core)
nil
user=> (fingerprint {:x 1})
"e3b3666589d6514c9b10d677bf7320426cfbab2c"
```

## License

Copyright © 2013 James Reeves

Distributed under the Eclipse Public License, the same as Clojure.
