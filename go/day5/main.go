package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type Range struct {
	To  uint    // map to
	In  [2]uint // in range
	Len uint
}

func getRanges(mappings []string) [][]Range {
	rangesList := [][]Range{}

	for _, mapping := range mappings {
		rangeList := []Range{}
		mappingParsed := strings.Split(strings.Split(mapping, ":\n")[1], "\n")

		for _, m := range mappingParsed {
			strs := strings.Split(m, " ")
			parsed := []uint{}

			for _, numStr := range strs {
				num, err := strconv.Atoi(numStr)
				if err != nil {
					panic(err)
				}

				parsed = append(parsed, uint(num))
			}

			rangeList = append(rangeList, Range{
				To: parsed[0],
				In: [2]uint{parsed[1], parsed[1] + parsed[2]},
			})
		}

		rangesList = append(rangesList, rangeList)
	}

	return rangesList
}

// using u
func getLocation(p uint, ranges []Range) uint {
	/*
		if (x >= MIN && x <= MAX) { ... } most efficient if x >= MIN is hardly ever true
		if (x <= MAX && x >= MIN) { ... } most efficient if x <= MAX is hardly ever true
	*/
	for _, r := range ranges {
		if p <= r.In[1] && p >= r.In[0] {
			return r.To + (p - r.In[0])
		}
	}

	return p
}

func part1(input string) {
	parts := strings.Split(strings.TrimSpace(input), "\n\n")
	seeds := strings.Split(strings.Split(parts[0], ": ")[1], " ")
	rangesList := getRanges(parts[1:])

	var finalLoc uint = 0

	for _, seedStr := range seeds {
		seed, err := strconv.Atoi(seedStr)
		if err != nil {
			panic(err)
		}

		loc := uint(seed)

		for _, ranges := range rangesList {
			loc = getLocation(loc, ranges)
		}

		if finalLoc == 0 || loc < finalLoc {
			finalLoc = loc
		}
	}

	fmt.Println("part1: ", finalLoc)
}

/*
func getSoils(seeds [][2]uint, ranges []Range) (soils []uint) {
	seenSoils := map[uint]bool{}

	for _, seed := range seeds {
		for _, r := range ranges {
			if (seed[0] <= r.In[1] && seed[0] >= r.In[0]) &&
				(seed[0]+seed[1] <= r.In[1] && seed[0]+seed[1] >= r.In[0]) {
				soil := r.To + r.Len

				if !seenSoils[soil] {
					soils = append(soils, soil)
					seenSoils[soil] = true
				}

				break
			} else if seed[0] <= r.In[1] && seed[0] >= r.In[0] {
				// only a group of the seeds are in the range, from left
				soil := r.To + r.Len

				if !seenSoils[soil] {
					soils = append(soils, soil)
					seenSoils[soil] = true
				}

				seeds = append(seeds, [2]uint{r.In[1] + 1, seed[0] + seed[1] - r.In[1] - 1})

				break
			} else if seed[0]+seed[1] <= r.In[1] && seed[0]+seed[1] >= r.In[0] {
				// only a group of the seeds are in the range, from right
				soil := r.To + r.Len

				if !seenSoils[soil] {
					soils = append(soils, soil)
					seenSoils[soil] = true
				}

				seeds = append(seeds, [2]uint{seed[0], r.In[0] - seed[0]})

				break
			}
		}

		// not in any range, return seed value
		for i := 0; i < len(seed); i++ {
			soil := seed[0] + uint(i)

			if !seenSoils[soil] {
				soils = append(soils, soil)
				seenSoils[soil] = true
			}
		}
	}

	return
}
*/

func part2(input string) {
	/*
		seedRanges := [][2]uint{}
		for i := 0; i < len(seedsStr); i += 2 {
			src, err := strconv.Atoi(seedsStr[i])
			if err != nil {
				panic(err)
			}
			len, err := strconv.Atoi(seedsStr[i+1])
			if err != nil {
				panic(err)
			}
			seedRanges = append(seedRanges, [2]uint{uint(src), uint(len)})
		}

		rangesList := getRanges(parts[1:])

		var finalLoc uint = 0

		soils := getSoils(seedRanges, rangesList[0])

		fmt.Println(soils)

		rangesList = rangesList[1:]

		for _, soil := range soils {
			loc := soil

			for _, ranges := range rangesList {
				loc = getLocation(loc, ranges)
			}

			if finalLoc == 0 || loc < finalLoc {
				finalLoc = loc
			}
		}

		fmt.Println("part2: ", finalLoc)
	*/
	parts := strings.Split(strings.TrimSpace(input), "\n\n")
	seedsStr := strings.Split(strings.Split(parts[0], ": ")[1], " ")
	seedRanges := [][2]uint{}
	for i := 0; i < len(seedsStr); i += 2 {
		src, err := strconv.Atoi(seedsStr[i])
		if err != nil {
			panic(err)
		}
		len, err := strconv.Atoi(seedsStr[i+1])
		if err != nil {
			panic(err)
		}
		seedRanges = append(seedRanges, [2]uint{uint(src), uint(len)})
	}

}

func main() {
	file, _ := os.ReadFile("./input.txt")

	part2(string(file))
}
