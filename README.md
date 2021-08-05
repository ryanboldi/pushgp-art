# PushGP Art

Using the Push Genetic Programming system to evolve Compositional Pattern Producing Networks.

[PushGP](https://faculty.hampshire.edu/lspector/push.html) is a genetic programming system that evolves push programs. The implementation used in this project can be found [here](https://github.com/lspector/propeller).

Compositional Pattern Producing Networks, first proposed in [this paper](http://eplex.cs.ucf.edu/papers/stanley_aaaifs06.pdf), are a form of indirect encodings for genotype to phenotype mapping. 

Due to these networks' fundamental functional basis, implementing them in Clojure using the PushGP evolutionary framework seems to be an obvious direction to move in.

## Random First Generation

![random-64](/assets/64-random.png)

## Usage

LightTable - open `core.clj` and press `Ctrl+Shift+Enter` to evaluate the file.

Emacs - run cider, open `core.clj` and press `C-c C-k` to evaluate the file.

REPL - run `(require 'pushgp-art.core)`.

## License

Copyright © 2021 Ryan Boldi

Distributed under the Eclipse Public License 1.0
