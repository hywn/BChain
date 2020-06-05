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

			counters.forEach(ar => ar.push(0)) // expand all existing nodes by 1

			currIndex = words.length // recognize new entry

			words.push(word) // add word to word list

			counters.push(new Array(words.length).fill(0)) // add new node to counters

		}

		// updates last word's counter
		if (lastIndex != -1)
			counters[lastIndex][currIndex] += 1

		lastIndex = currIndex; // curr word will be previous word for next word

	})

	console.log(counters)

	// convert counts into probabilities
	const probabilities = counters.map(word_counts => {

		// || 1 to prevent division by 0
		const sum = word_counts.reduce((total, x) => total + x) || 1

		return word_counts.map(count => count/sum)

	})

	// return everything
	return { words, probabilities }
}

function generateText(source, length, firstWord)
{
	const { words, probabilities } = getChain(source)

	console.log(words)

	let text = ""

	let currIndex = words.indexOf(firstWord)

	for (let i=0; i < length; i++) {

		text += words[currIndex]
		text += " "

		currIndex = getWeightedIndex(probabilities[currIndex])

		console.log(i)

	}

	return text
}

function getWeightedIndex(node)
{
	const rand = Math.random()

	let sum = 0

	for (const i in node) {

		sum += node[i]

		if (sum >= rand)
			return i

	}

	return 0
}