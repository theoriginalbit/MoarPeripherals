#!/bin/bash

echo ================ THEORIGINALBIT RESOURCE PACK BUILDER ================
echo Which resourcepack would you like to zip? 

read file

a='MoarPeripherals '
b=' Program.zip'

zipFile=$a$file$b

if [ -e $file ]; then
	if [ -d $file ]; then
		echo ============================ ZIPPING PACK ============================
		cd $file
		zip -r "$zipFile" . -x "*.DS_Store"
		cd ..
		mv "$file/$zipFile" .
		echo ============================== COMPLETE ==============================
		echo Pack created as \'$zipFile\'
	else
		echo Not a folder
	fi
else
	echo File not found!
fi