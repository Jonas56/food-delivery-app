import { Controller, Get, Param } from '@nestjs/common';
import { MenusService } from './menus.service';
import { Query } from '@nestjs/common/decorators';
import { PaginationQueryDto } from 'src/common/dto/pagination-query';

@Controller('menus')
export class MenusController {
  constructor(private readonly menusService: MenusService) {}

  @Get()
  findAll(@Query() paginationQuery: PaginationQueryDto) {
    return this.menusService.findAll(paginationQuery);
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.menusService.findOne(id);
  }

  // @Post()
  // create(@Body() createMenuDto: CreateMenuDto) {
  //   return this.menusService.create(createMenuDto);
  // }

  // @Patch(':id')
  // update(@Param('id') id: string, @Body() updateMenuDto: UpdateMenuDto) {
  //   return this.menusService.update(+id, updateMenuDto);
  // }

  // @Delete(':id')
  // remove(@Param('id') id: string) {
  //   return this.menusService.remove(+id);
  // }
}
