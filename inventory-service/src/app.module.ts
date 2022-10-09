import { Module } from '@nestjs/common';
import { MenusModule } from './menus/menus.module';

@Module({
  imports: [MenusModule],
})
export class AppModule {}
