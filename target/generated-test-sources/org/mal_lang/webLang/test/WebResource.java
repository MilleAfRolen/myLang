package org.mal_lang.webLang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public abstract class WebResource extends Asset {
  public Access access;

  public WebResource(String name) {
    super(name);
    assetClassName = "WebResource";
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public WebResource() {
    this("Anonymous");
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
        if (WebResource.this instanceof ScriptResource) {
          if (((org.mal_lang.webLang.test.ScriptResource) WebResource.this).webserver != null) {
            _cacheParentAccess.add(((org.mal_lang.webLang.test.ScriptResource) WebResource.this).webserver.accessServerScripts);
          }
        }
        if (WebResource.this instanceof ProtectedResource) {
          if (((org.mal_lang.webLang.test.ProtectedResource) WebResource.this).server != null) {
            _cacheParentAccess.add(((org.mal_lang.webLang.test.ProtectedResource) WebResource.this).server.access);
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
