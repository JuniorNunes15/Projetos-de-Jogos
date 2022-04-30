#game setup
from multiprocessing.context import ForkServerProcess


WIDTH = 1080#1280
HEIGTH = 620
FPS = 60
TILESIZE = 64

WORLD_MAP = [
    ['x','x','x','x','x'],
    ['x',' ',' ',' ','x'],
    ['x',' ',' ',' ','x'],
    ['x',' ','p',' ','x'],
    ['x',' ',' ',' ','x'],
    ['x','x','x','x','x'],
]