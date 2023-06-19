package org.mal_lang.webLang.test;

import core.Asset;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class ProtectedResource extends WebResource {
  public WebServer server = null;

  public Set<Account> account = new HashSet<>();

  public ProtectedResource(String name) {
    super(name);
    assetClassName = "ProtectedResource";
  }

  public ProtectedResource() {
    this("Anonymous");
  }

  public void addServer(WebServer server) {
    this.server = server;
    server.resource.add(this);
  }

  public void addAccount(Account account) {
    this.account.add(account);
    account.resource.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("server")) {
      return WebServer.class.getName();
    } else if (field.equals("account")) {
      return Account.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("server")) {
      if (server != null) {
        assets.add(server);
      }
    } else if (field.equals("account")) {
      assets.addAll(account);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (server != null) {
      assets.add(server);
    }
    assets.addAll(account);
    return assets;
  }
}
