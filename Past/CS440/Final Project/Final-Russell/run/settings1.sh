#!/bin/bash

EXPERIMENT_LABEL=$(date +%Y%m%d%H%M)
TRAIN_DIR=train/$EXPERIMENT_LABEL
SETTINGS_LOG_DIR=$TRAIN_DIR/logs
HYPERPARAMS="--feed_previous --feature_matching --bidirectional_d --learning_rate 0.1 --pretraining_epochs 6 --num_layers_d 3 --random_input_scale 1.5  --tones_per_cell 3 --end_classification"
mkdir -p $SETTINGS_LOG_DIR

