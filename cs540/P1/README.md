So here is a quick version of a sort-of simulator that I have at the moment that I abstracted from the search/planning stuff
And still not working off dictionaries because I don't think they do lists sadly?  And not object oriented at the moment

It may need to read blocks from a file? eventually in the future but for now is set up sort of as a demo / proof of numpy concepts and such
It included some random block generation overhead at the moment as well

Please take a look if ya get a chance

At the moment, get valid move is a mess due to the nature of checking 6 possible paths to move, say, [0,0,0] to [1,1,1] if some are blocked.  Additionally, if the drone is carrying a block, the 'color' of the block it is carrying below it should not make it think it can't descend as long as what is below that is free.  Same concept with block being carried etc.

--

Additionally, I've added another file, which of course has overlap with the first at the moment called DroneWorldShallowSearchDemo

It demonstrates the initialization of a blocks, their conversion to a field, finding every possible red block capable of being picked up and a not-so-fast-but-not-so-slow complete, uniform-cost, shallowest expansion first search algorithm that shows the nearest possible block as measured by the number of moves to pick up given a start location, the ~several thousand possible red blocks to pick up, and ensures no obstactles are in the way as well.
Note: the number of moves are measured as per cube.  That is [0,0,0] to [1,1,1] is three moves.  
Note: despite being a complete yet first-find-break algorithm, it still takes a while if the drone is at the ceiling