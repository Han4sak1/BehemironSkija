#! /usr/bin/env python3
import build_utils, common, os, sys

def main():
  os.chdir(common.basedir)

  build_utils.release_notes(common.version)

  for name in [common.shared_artifact] + [f'{common.platform_artifact_prefix}-{classifier}' for classifier in ['windows-x64', 'windows-arm64', 'linux-x64', 'linux-arm64', 'macos-x64', 'macos-arm64', 'android-arm64', 'android-x64']]:
    jars = [f"target/{name}-{common.version}{classifier}.jar" for classifier in ["", "-sources", "-javadoc"]]
    build_utils.collect_jars(common.maven_group, name, common.version, jars, 'target/release')

  return build_utils.release(f"behemiron-skija-{common.version}.zip", 'target/release')

if __name__ == "__main__":
  sys.exit(main())
