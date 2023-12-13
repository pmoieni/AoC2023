package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

func getTotalRecords(time int, distance int) (records int) {
	// the middle number always gives the best record
	middle := float64(time) / float64(2)
	floor := int(math.Floor(float64(middle)))
	for i := floor; i > 0; i-- {
		if ((time - i) * i) > distance {
			records += 2
		} else {
			break
		}
	}

	// if time is even we need to remove a duplicate record
	if middle == float64(floor) {
		records -= 1
	}

	return records
}

func part1(input string) {
	lines := strings.Split(input, "\n")
	times := strings.Fields(strings.Split(lines[0], ":")[1])
	distances := strings.Fields(strings.Split(lines[1], ":")[1])

	records := 1
	for i, timeStr := range times {
		time, _ := strconv.Atoi(timeStr)
		distance, _ := strconv.Atoi(distances[i])
		records *= getTotalRecords(time, distance)
	}

	fmt.Println("part1: ", records)
}

func part2(input string) {
	lines := strings.Split(input, "\n")
	timeStr := strings.Join(strings.Fields(strings.Split(lines[0], ":")[1]), "")
	distanceStr := strings.Join(strings.Fields(strings.Split(lines[1], ":")[1]), "")

	time, _ := strconv.Atoi(timeStr)
	distance, _ := strconv.Atoi(distanceStr)

	fmt.Println("part2: ", getTotalRecords(time, distance))
}

func main() {
	file, _ := os.ReadFile("./input.txt")

	part2(string(file))
}
