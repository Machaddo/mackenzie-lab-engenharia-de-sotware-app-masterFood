import { bootstrapApplication } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { routes } from 'app.routes'
import { MainLayoutComponent } from "core/layout/main-layout.component";

bootstrapApplication(MainLayoutComponent, {
  providers: [
    provideRouter(routes),
    importProvidersFrom(CommonModule, FormsModule)
  ],
}).catch((err) => console.error(err));
