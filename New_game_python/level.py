import pygame
from settings import *
from tile import Tile
from player import Player
from debug import debug

class Level:
    def __init__(self):
        
        #get the display surface
        self.display_surface = pygame.display.get_surface()

        #sprite group setup
        self.visible_sprites = YsortCameraGroup()
        self.obstacles_sprites = pygame.sprite.Group()

        #sprite setup
        self.creat_map()

    def creat_map(self):
        for row_index, row in enumerate(WORLD_MAP):
            for col_index, col in enumerate(row):
                x = col_index * TILESIZE
                y = row_index * TILESIZE
                if col == 'x':
                    Tile((x,y),[self.visible_sprites, self.obstacles_sprites])
                if col == 'p':
                    self.player = Player((x,y),[self.visible_sprites], self.obstacles_sprites)

    def run(self):
        #update and draw the game
        self.visible_sprites.custom_draw()
        self.visible_sprites.update()
        #debug(self.player.direction)
        

class YsortCameraGroup(pygame.sprite.Group):
    def __init__(self):
        #general setups
        super().__init__()
        self.display_surface = pygame.display.get_surface()
        self.half_width = self.display_surface.get_size()[0] // 2
        self.half_height = self.display_surface.get_size()[1] // 2
        self.offset = pygame.math.Vector2()

    def custom_draw(self, player):
        self.offset.x = player.rect.centerx
        self.offset.y = player.rect.centery

        for sprite in self.sprites():
            offset_pos = sprite.rect.topleft + self.offset
            self.display_surface.blit(sprite.image,offset_pos)