import { bootstrapApplication } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { routes } from 'app.routes'
import { MainLayoutComponent } from "core/layout/main-layout.component";
import { 
  provideHttpClient, 
  withInterceptors 
} from '@angular/common/http';
import { 
  authInterceptor 
} from 'core/services/auth/auth.interceptor' 

bootstrapApplication(MainLayoutComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
        authInterceptor
      ])
    ),
    importProvidersFrom(CommonModule, FormsModule)
  ],
}).catch((err) => console.error(err));
