#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -lt 2 ]; then
    echo "Usage: $0 <number_of_repetitions> <command>"
    exit 1
fi

# Arguments
repetitions=$1
shift
command="$@"

# Validate repetitions is a positive integer
if ! [[ "$repetitions" =~ ^[0-9]+$ ]] || [ "$repetitions" -le 0 ]; then
    echo "Error: The number of repetitions must be a positive integer."
    exit 1
fi

# Initialize total time
total_time=0

# Execute the command the specified number of times
for ((i = 1; i <= repetitions; i++)); do
    start_time=$(date +%s%N)
    eval $command
    end_time=$(date +%s%N)

    # Calculate elapsed time in nanoseconds
    elapsed_time=$((end_time - start_time))
    total_time=$((total_time + elapsed_time))

    echo "Run #$i: $(bc <<< "scale=3; $elapsed_time / 1000000000") seconds"
done

# Calculate and display the average time
average_time=$(bc <<< "scale=3; $total_time / $repetitions / 1000000000")
echo "Average time: $average_time seconds"
