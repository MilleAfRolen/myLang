package org.mal_lang.webLang.test;

import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;

public class protectedResource extends Information {
  public Access access;

  public protectedResource(String name) {
    super(name);
    assetClassName = "protectedResource";
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
  }

  public protectedResource() {
    this("Anonymous");
  }

  public class Access extends AttackStepMin {
    public Access(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("protectedResource.access");
    }
  }

  public class Deny extends Information.Deny {
    public Deny(String name) {
      super(name);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("protectedResource.deny");
    }
  }
}
