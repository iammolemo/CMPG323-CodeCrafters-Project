import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { UpdateuserComponent } from './updateuser/updateuser.component';
import { UserslistComponent } from './userslist/userslist.component';
import { usersGuard, adminGuard } from './users.guard';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { ContributeComponent } from './contribute/contribute.component';
import { ContributorsComponent } from './contributors/contributors.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FaqComponent } from './faq/faq.component';
import { ModerateComponent } from './moderate/moderate.component';
import { OtherOersComponent } from './otheroers/otheroers.component';
import { ResetpassComponent } from './resetpass/resetpass.component';
import { SelflearningComponent } from './selflearning/selflearning.component';
import { SubjectViewComponent } from './subjectview/subjectview.component';
import { SearchComponent } from './search/search.component';



export const routes: Routes = [
    {path: 'landingpage', component: LandingpageComponent},
    {path: 'aboutus', component: AboutusComponent},
    {path: 'analytics', component: AnalyticsComponent},
    {path: 'contribute', component: ContributeComponent},
    {path: 'contributors', component: ContributorsComponent},
    {path: 'faq', component: FaqComponent},
    {path: 'moderate', component: ModerateComponent},
    {path: 'otheroers', component: OtherOersComponent},
    {path: 'resetpass', component: ResetpassComponent},
    {path: 'selflearning', component: SelflearningComponent},
    {path: 'subjectview', component: SubjectViewComponent},
    {path: 'search', component: SearchComponent },
    {path: 'dashboard', component: DashboardComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'profile', component: ProfileComponent},
    {path: 'update/:id', component: UpdateuserComponent},
    {path: 'users', component: UserslistComponent },
    {path: '**', component: LandingpageComponent},
    {path: '', redirectTo: '/landingpage', pathMatch: 'full'},
];





/*canActivate:[adminGuard]*/