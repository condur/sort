### Background

The project has to do with data integration.

A simplified example: product specifications might come from one data source, product pricing from another, and product reviews from a third. A major challenge in working with all this data is determining when two pieces of information from disparate data sources are actually talking about the same product. In academic circles this problem is sometimes called Record Linkage, Entity Resolution, Reference Reconciliation, or a host of other fancy terms. We describe this problem very technically as “matching.”

### The Challenge

The goal is to match product listings from a 3rd party retailer, e.g. “Nikon D90 12.3MP Digital SLR Camera (Body Only)” against a set of known products, e.g. “Nikon D90”.
There is a set of products and a set of price listings matching some of those products. The task is to match each listing to the correct product. Precision is critical. It is prefered missed matches (lower recall) over incorrect matches, avoiding false positives. A single price listing may match at most one product.

##### Project Scope

- Readable, efficient code
- Precision – many false matches?
- Recall – how many correct matches?
- Appropriate data structure and algorithm choices

The inputs and outputs for this challenge are in the so-called JSON lines format. That is, one valid JSON object per line, with newline separators.

##### Challenge Data
Contains two files:
products.txt – Contains around 700 products
listings.txt – Contains about 20,000 product listings

### Input Files
Products file (products.txt).
Text file with one Product object per line.

__Product__
{
"product_name": String // A unique id for the product
"manufacturer": String
"family": String // optional grouping of products
"model": String
"announced-date": String // ISO-8601 formatted date string, e.g. 2011-04-28T19:00:00.000-05:00
}

Listings file (listings.txt).
Text file with one Listing object per line.

__Listing__
{
"title": String // description of product for sale
"manufacturer": String // who manufactures the product for sale
"currency": String // currency code, e.g. USD, CAD, GBP, etc.
"price": String // price, e.g. 19.99, 100.00
}

### Output File
The output your solution creates should be a text file with one Result object per line.

__Result__
A file full of Result objects is what your solution will be generating. A Result simply associates a Product with a list of matching Listing objects.
{
"product_name": String
"listings": Array[Listing]
}

### Run the application

The projec is configured to run using boot-clj: https://github.com/boot-clj/boot and is using the alpha version of Clojure: 1.9.0-alpha20. Please make sure the set in your $HOME/.boot/boot.properties file the same Clojure version.

To run the project excute in the command line:

```
boot run
```

### Project structure

* core - the main 'match' function and helpers.
* spec - clojure specs, that are using to validate the input data
* data - logic of reading and validating the input data: products and listings from resource folder
* similarity - logic used to find the best match between various listing and products
* file - logic related to read and write to files
* transducers and util - a collection of helper functions 
