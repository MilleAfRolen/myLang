package org.mal_lang.webLang.test;

import core.Asset;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class ScriptResource extends WebResource {
  public WebServer webserver = null;

  public ScriptResource(String name) {
    super(name);
    assetClassName = "ScriptResource";
  }

  public ScriptResource() {
    this("Anonymous");
  }

  public void addWebserver(WebServer webserver) {
    this.webserver = webserver;
    webserver.scripts.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("webserver")) {
      return WebServer.class.getName();
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
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (webserver != null) {
      assets.add(webserver);
    }
    return assets;
  }
}
