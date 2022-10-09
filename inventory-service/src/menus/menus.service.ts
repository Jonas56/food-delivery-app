import { Injectable } from '@nestjs/common';
import axios from 'axios';
import { Menu } from './entities/menu.entity';
import { PaginationQueryDto } from 'src/common/dto/pagination-query';
import { NotFoundException } from '@nestjs/common/exceptions';

@Injectable()
export class MenusService {
  async findAll(paginationQuery: PaginationQueryDto): Promise<Menu[]> {
    const { limit = 0, offset = 0 } = paginationQuery;

    try {
      if (limit || offset) {
        const { data } = await axios.get(
          `https://ig-food-menus.herokuapp.com/burgers?_limit=${limit}&_start=${offset}`,
        );
        return data;
      }
      const { data } = await axios.get(
        `https://ig-food-menus.herokuapp.com/burgers`,
      );
      return data;
    } catch (err) {
      console.log(err);
    }
  }

  async findOne(id: string): Promise<Menu> {
    try {
      const { data } = await axios.get(
        `https://ig-food-menus.herokuapp.com/burgers/${id}`,
      );
      return data;
    } catch (err) {
      throw new NotFoundException(`Menu #${id} not found`);
    }
  }
  // create(createMenuDto: CreateMenuDto) {
  //   return 'This action adds a new menu';
  // }

  // update(id: number, updateMenuDto: UpdateMenuDto) {
  //   return `This action updates a #${id} menu`;
  // }

  // remove(id: number) {
  //   return `This action removes a #${id} menu`;
  // }
}
