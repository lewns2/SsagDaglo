#!/bin/sh

# $1: 유튜브 URL
# $2: 파일이름 + .mp4

#cd /var/app/current/
cd ~
mkdir upload
cd upload
wget https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp
chmod u+x yt-dlp # 실행 권한 문제
# ./yt-dlp -f 140 https://www.youtube.com/watch?v=doYeFZ6saRY -o test.m4a
./yt-dlp -f 140 $1 -o $2