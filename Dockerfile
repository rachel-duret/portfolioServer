RUN nix-env -iA nixpkgs.coreutils nixpkgs.bash
COPY . /app/.
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
