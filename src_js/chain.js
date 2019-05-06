function getChain (text){

	var words = new Array();
	var counters = new Array();

	var lastIndex = -1;

	for (word in text.split("")) {

		var index = words.indexOf(word);

		if (index == -1) {

			for (node in counters)
				node.push(0);

			index = words.length;

			counters.add(new Array(words.length + 1).fill(0));

			words.push(word);

		}

		if (lastIndex != -1) counters[lastIndex][index] = counters[lastIndex][index] + 1;

		lastIndex = index;

	}

	var probabilities = new Array();

	for (int x=0; x < counter.length; x++) {

		var node = new Array();

		var sum = counters[x].reduce();

		if (sum != 0)
			for (int i=0; i < counters[x].length; i++) probabilities[x][i] = counters[x][i]/sum;
		else
			probabilities[x] = new Array(counters[x].length).fill(0);

	}

	return {
		words: words;
		probabilities: probabilities;
	}

}

print(getChain("hello my dudes"));