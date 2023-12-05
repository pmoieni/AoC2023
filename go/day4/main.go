package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strings"
)

func getWinsCount(a []string, b []string) (count int) {
	hash := make(map[string]struct{})

	for _, v := range a {
		hash[v] = struct{}{}
	}

	for _, v := range b {
		if _, ok := hash[v]; ok {
			count++
		}
	}

	return
}

func calcPoints(wins int) int {
	if wins < 1 {
		wins = 0
	}

	return int(math.Pow(2, float64(wins-1)))
}

func part1(scanner *bufio.Scanner) {
	sum := 0
	for scanner.Scan() {
		card := strings.Split(strings.Split(scanner.Text(), ": ")[1], " | ")
		points := calcPoints(
			getWinsCount(
				strings.Fields(card[0]),
				strings.Fields(card[1]),
			),
		)
		sum += points
	}

	fmt.Println("part1: ", sum)
}

// TODO: process copiesLeft inside getWinsCount
func part2(scanner *bufio.Scanner) {
	totalCards := 0
	copiesLeft := []int{} // copies not processed yet

	for scanner.Scan() {
		totalCards += 1
		if len(copiesLeft) != 0 {
			totalCards += copiesLeft[0]
		}
		card := strings.Split(strings.Split(scanner.Text(), ": ")[1], " | ")
		wins := getWinsCount(strings.Fields(card[0]), strings.Fields(card[1]))
		lastCopies := 0
		// remove, already used
		if len(copiesLeft) != 0 {
			lastCopies, copiesLeft = copiesLeft[0], copiesLeft[1:]
		}
		for i := 0; i < wins; i++ {
			if !(len(copiesLeft) > i) {
				copiesLeft = append(copiesLeft, lastCopies+1)
			} else {
				copiesLeft[i] += lastCopies + 1
			}
		}
	}

	fmt.Println("part2: ", totalCards)
}

func main() {
	file, _ := os.Open("./input.txt")
	defer file.Close()

	scanner := bufio.NewScanner(file)

	part2(scanner)
}
