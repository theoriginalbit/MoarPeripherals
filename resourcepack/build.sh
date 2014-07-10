#!/bin/bash
echo Which resourcepack would you like to zip? 

read file

a='MoarPeripherals '
b=' Program.zip'

zipFile=$a$file$b

if [ -e $file ]; then
	if [ -d $file ]; then
		zip -r "$zipFile" $file -x "*.DS_Store"
	else
		echo Not a folder
	fi
else
	echo File not found!
fi