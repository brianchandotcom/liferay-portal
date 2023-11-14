import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ConfigService} from "../config.service";

declare const Liferay:any;
@Injectable({
  providedIn: 'root'
})
export class TemplateServicesService {

  agentOauthAppId = 'liferay-dlfoldertemplate-oauth-application-user-agent';
  suggestServerUrl() {
    let OAuthApp = Liferay
        .OAuth2Client
        .FromUserAgentApplication(this.agentOauthAppId)
        .homePageURL;
    return OAuthApp;
  }
  public async createFolder(templateId:any,containerId:any,rootName:any) {
      let accessToken = (await Liferay.OAuth2Client
          .FromUserAgentApplication(this.agentOauthAppId)
          ._getOrRequestToken()).access_token;
      const prom = new Promise((resolve, reject) => {

      const requestOptions = {
          headers: new HttpHeaders({
              'Authorization': `Bearer ${accessToken}`
          })
      };
      try {
          console.log(`${this.suggestServerUrl()}
              ${this.configService.configObject["folder.generate.service.url"]}
              /${templateId}/${containerId}/${rootName}`);
              this.http
                  .post(
                      `${this.suggestServerUrl()}${this.configService.configObject["folder.generate.service.url"]}/${templateId}/${containerId}/${rootName}`,
                      null,requestOptions
                  )
                  .subscribe(
                      (result) => {
                          console.log('success');
                          resolve(result);
                      },
                      (error) => {
                          reject(error);
                      }
                  );

      }catch (exp)
      {
          console.log(exp);
          reject(exp);
      }
      });
      return prom;

  }
  constructor(private http:HttpClient,private configService:ConfigService) {

  }
}
