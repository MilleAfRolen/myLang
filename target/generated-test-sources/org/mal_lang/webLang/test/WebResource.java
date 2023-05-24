package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class WebResource extends Asset {
  public Access access;

  public WebServer server = null;

  public WebResource(String name) {
    super(name);
    assetClassName = "WebResource";
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public WebResource() {
    this("Anonymous");
  }

  public void addServer(WebServer server) {
    this.server = server;
    server.webResource.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("server")) {
      return WebServer.class.getName();
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
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (server != null) {
      assets.add(server);
    }
    return assets;
  }

  public class Access extends AttackStepMin {
    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccess == null) {
        _cacheParentAccess = new HashSet<>();
        if (WebResource.this instanceof WebPage) {
          if (((org.mal_lang.webLang.test.WebPage) WebResource.this).webserver != null) {
            _cacheParentAccess.add(((org.mal_lang.webLang.test.WebPage) WebResource.this).webserver.connect);
          }
        }
        if (server != null) {
          _cacheParentAccess.add(server.accessServerScripts);
        }
        if (WebResource.this instanceof ProtectedResource) {
          if (((org.mal_lang.webLang.test.ProtectedResource) WebResource.this).webserver != null) {
            _cacheParentAccess.add(((org.mal_lang.webLang.test.ProtectedResource) WebResource.this).webserver.access);
          }
        }
        if (WebResource.this instanceof ProtectedResource) {
          for (Account _0 : ((org.mal_lang.webLang.test.ProtectedResource) WebResource.this).userAccount) {
            _cacheParentAccess.add(_0.compromise);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("WebResource.access");
    }
  }
}
