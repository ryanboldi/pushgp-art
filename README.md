# PushGP Art

Using the Push Genetic Programming system to evolve Compositional Pattern Producing Networks.

[PushGP](https://faculty.hampshire.edu/lspector/push.html) is a genetic programming system that evolves push programs. The implementation used in this project can be found [here](https://github.com/lspector/propeller).

Compositional Pattern Producing Networks (CPPNs), first proposed in [this paper](http://eplex.cs.ucf.edu/papers/stanley_aaaifs06.pdf), are a form of indirect encodings for genotype to phenotype mapping. 

Due to these networks' fundamental functional basis, implementing them in Clojure using the PushGP evolutionary framework seems to be an obvious direction to move in.

<h1 align=center> Push Genetic Programming (PushGP) </h1>

'Push' is a programming language that was specifically designed for use in evolutionary computation. For a quick introduction, [this](https://www.youtube.com/watch?v=VGJWlSC0gl4&t=13s) youtube video is a great resource.

In short, a Push program is a series of instructions that are carried out on a set of **stacks**. Each datatype (int, float, char, bool, etc) exists on its own stack. For the purposes of this project, only the float datatype stack was used. Along with datatypes, instructions also exist on their own stack. This allows for instruction duplication, if statements, and more complex computational paradigms.

The push implementation that was used in this project was written in Clojure, a Lisp dialect and can be found here: https://github.com/lspector/propeller.

For this project, 3 new instructions were added to the propeller library. Namely, sigmoid, gaussian and mod(0.5, x). Adding these functions helped promote more symmetry and ragged periodicity.

<h1 align=center> Compositional Pattern Producing Networks (CPPNs) </h1>

### What?
For this project's use case, a CPPN can be thought of as a function of two variables:

CPPN(x, y) = output

Where (x,y) represents the coordinates of a single pixel in the outputted image, and the output is a pixel greyscale value from 0 to 1 to color this pixel with.

### How?

The genotype of these CPPNs are stored as **plushy** (linear push) programs. The push interpretter converts these plushies into a set of functional push programs, each representing a CPPN. This CPPN is then run w*w times (once for each pixel in a w x w pixel image), and the output is displayed. Below is an example of 16 completely random 128x128 images produced by 16 different CPPNs from push programs:

![example 1](assets/example-1.png)

### Why?

This **indirect encoding** (called so due to the non-one-to-one mapping between genotype and phenotype), allows for a significant amount of compression of information. This compression is important when considering artificial life and how evolution took place in our own history. Things like bilateral symmetry, repeating patterns, or repetition with slight variation, are all due to certain genes being reused in special ways throughout our phenotype. This might explain how the ~30,000 genes in the human body are able to encode the 86+ billion neurons in the human brain (let alone the rest of the body's functions).

This means that we are able to use a relatively small CPPN (100 or so push instructions) to encode images with more than 1,048,576 pixels (1024x1024).

There is, in fact, no theoretical maximum resolution for the output of the CPPN. The only limiting factor is that the higher the resolution, the more times the CPPN must be run to generate the output. The interesting thing is that a trained CPPN will output the same image to higher resolutions __without any further evolution__. This is why images should be evolved in lower resolution (64x64 or 128x128), and then can be displayed at higher resolutions once a good solution is found.

For example, the 3rd outputted image in the previous section looks interesting. Here is the plushy that produced it: 
```
 (:float_add :float_subtract :exec_dup :float_gt :float_gt :float_cos :exec_dup :float_lt :float_cos :float_max :float_gauss 1 :float_mod1 close :float_sin :float_quot :exec_if :in2 :float_add close :float_lt :in1 :float_mult :float_subtract :in1 :in2 :float_lt :in1 :float_subtract :exec_dup :in2 :float_mod1 :exec_if :in1 0 :float_gt close :float_max :float_lt :exec_dup :float_mult :exec_dup :float_sin :float_quot 0 :float_min :exec_if :float_tan :float_mod1 :float_mod1 :in1 :float_gauss :float_min :float_quot :float_mod1 :float_sin :float_quot :float_min :float_gauss close :float_mod1 :float_gt 1 :float_subtract 0 0 0 1 :float_add :float_subtract :float_mult :float_subtract :float_quot :float_gauss :exec_dup :float_tan :float_mod1 :float_subtract :exec_dup :float_tan :float_subtract :float_sin close :float_quot close :float_min :in2 :float_add :float_mod1 :float_mod1)
 ```
Here it is at 10x10 resolution:

<img src="assets/examples/10x10.png" alt="drawing" width="256"/>

Here it is at 64x64:

<img src="assets/examples/64x64.png" alt="drawing" width="256"/>

256x256:

<img src="assets/examples/256x256.png" alt="drawing" width="256"/>

And finally, 1024x1024:

<img src="assets/examples/1024x1024.png" alt="drawing" width="1024"/>

----

<h1 align=center> Evolution of Art </h1>

This project is focused on various forms of open ended evolution of CPPNs. There are two ways that CPPNs described by push programs are able to evolve: Mutation and Crossover.

### Mutation
 The following images have two rows, the first row contains the parents, and the second row contains the children that are produced from solely mutating their respective parent. 
 
 For this mutation, a mutation rate of 0.5 was used:

![4 Mutation only 0.5](assets/4-pure-mutation-05.png)

Here is an example with a mutation rate of 0.1:

![4 Mutation only 0.1](assets/4-pure-mutation-01.png)

and here is one with a mutation rate of 0.05:

![4 Mutation only 0.01](assets/4-pure-mutation-005.png)

### Crossover

Crossover is the mixing of two plushies to produce a genetically similar one. The following images show this taking place.

In this image, all 4 parents are randomly selected from to crossover to produce the children below:

![4-parents-4-children-cross](assets/4-parents-4-children-cross.png)

In this one, only the first two parents are crossed to produce the 4 children:

![only first 2 parents crossed](assets/only-first-2-parents-crossed.png)

## Usage

LightTable - open `core.clj` and press `Ctrl+Shift+Enter` to evaluate the file.

Emacs - run cider, open `core.clj` and press `C-c C-k` to evaluate the file.

REPL - run `(require 'pushgp-art.core)`.

Once the window is displaying, click on as many images as you would like to crossover. Selected images will have a red outline:

<img src="assets/evolution-example/000993.png" width="512"/>

 When ready, press <kbd> Enter </kbd>. Wait 1-2 minutes depending on the image resolution you chose. The next generation should display. Rinse and repeat.

 <img src="assets/evolution-example/001398.png" width="512"/>

If there is a specific image that you like and would like to save, hover over it with your mouse and press <kbd> s </kbd>. This plushy will be saved into plushies/plushy#.txt .Make sure you save this somewhere safe. 

To visualize a saved plushy, see visualize.clj, set the resolution and file path, and wait!

## License

Copyright © 2021 Ryan Boldi

Distributed under the Eclipse Public License 1.0
