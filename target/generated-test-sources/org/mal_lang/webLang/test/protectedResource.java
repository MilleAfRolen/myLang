package org.mal_lang.webLang.test;

import core.Asset;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class ProtectedResource extends WebResource {
  public WebServer webserver = null;

  public Set<Account> userAccount = new HashSet<>();

  public ProtectedResource(String name) {
    super(name);
    assetClassName = "ProtectedResource";
  }

  public ProtectedResource() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.resource.add(this);
  }

  public void addUserAccount(Account userAccount) {
    this.userAccount.add(userAccount);
    userAccount.resource.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
    } else if (field.equals("userAccount")) {
      return Account.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("webserver")) {
      if (webserver != null) {
        assets.add(webserver);
      }
    } else if (field.equals("userAccount")) {
      assets.addAll(userAccount);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
    }
    assets.addAll(userAccount);
    return assets;
  }
}
