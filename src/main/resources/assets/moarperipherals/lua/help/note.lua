textutils.pagedPrint([[Move up/down the list by either clicking on songs, using the mouse wheel, or via the directions on your keyboard. Songs must have a lowercase nbs extension to be detected! To start playback of the highlighted song, either click it, hit space/enter, or use the play button up top. (The latter toggles pause status if you already have a song loaded.)

The buttons to the right/left of the play/pause button skip forward/back, respectively. Skipping back will go to the start of your song, or if already at the start, then to the previous song (a history of up to 32 tracks is recorded). Skipping forward will start a new song according to your play mode selection (up the top right).

For keyboard users, the skip forward/back buttons can be triggered with your right/left direction keys. P can be used to pause, or Q/X/T to quit. R/N/M activate the different play modes - "repeat", "auto-next" and "mix" respectively.

If the currently playing song has an invalid tempo (and you're using a colour display), then the duration of the song is rendered in red. An invalid tempo may (or may not) adversely affect how playback sounds - Note Block Studio can be easily used to change the tempo of a file, if you wish. The player considers any factor of ten to be valid (integral or not), given that it can only process notes every tenth of a second (minimum).

Each Iron Note Block is currently capable of handling up to five instruments every "beat". Many songs play chords that exceed this, but connecting just two such peripherals to your system should allow the player to handle most (if not all) NBS files available (and it'll even use a few more blocks if you provide them, whether it needs to or not). If the player isn't provided with sufficient note blocks, it will simply skip notes as required.

Command line options:

note [path] [-r] [-n] [-m]

If a path is specified, the player will start with that as the active directory. If the path is a song file, it'll additionally attempt to play it.

-r, -n and -m each activate the "repeat", "auto-next" and "mix" play modes respectively. If one of the latter two are specified, the player will automatically select a new song to play on launch (meaning if you combine them with a song path, odds are a different song will play instead - it's recommended to stick with a directory (or no) path when using them).]])