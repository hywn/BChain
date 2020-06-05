function getChain(text)
{
	const text_words = text
		.toLowerCase()
		.replace(/([^\w ])/g, '') // removes nonalphanumeric (or space) characters
		.match(/\w+/g)            // gets all words

	const words    = [] // used for mapping word to word index
	const counters = [] // counter[word1 index][word2 index] = times word1 was followed by word2

	let lastIndex = -1 // index of last word that was processed by loop

	text_words.forEach(word => {

		let currIndex = words.indexOf(word) // gets index of current word

		if (currIndex == -1) { // if word hasn't been registered

			currIndex = words.length // recognize new entry

			words.push(word) // add word to word list

			// update counters
			counters.forEach(ar => ar.push(0))
			counters.push(new Array(words.length).fill(0))

		}

		// updates last word's counter
		if (lastIndex != -1)
			counters[lastIndex][currIndex] += 1

		lastIndex = currIndex; // curr word will be previous word for next word

	})

	// convert counts into probabilities
	const probabilities = counters.map(word_counts => {

		// || 1 to prevent division by 0
		const sum = word_counts.reduce((total, x) => total + x) || 1

		return word_counts.map(count => count / sum)

	})

	// return everything
	return { words, probabilities }
}

function generateText(text, length, firstWord)
{
	const { words, probabilities } = getChain(text)

	const indicies = [words.indexOf(firstWord)]

	for (let i = 1; i < length; i += 1)
		indicies[i] = getWeightedIndex(probabilities[indicies[i - 1]])

	return indicies.map(i => words[i]).join(' ')
}

function getWeightedIndex(node)
{
	const rand = Math.random()
	let   sum  = 0

	const index = node.findIndex(prob => (sum += prob) >= rand)

	return index == -1
		? 0
		: index
}