{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { nixpkgs, ... }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};
    in
    {
      devShells.${system}.default = pkgs.mkShell.override
        {
          stdenv = pkgs.clangStdenv;
        }
        {
          hardeningDisable = [ "all" ];

          packages = with pkgs; [
            bear
            lldb
            libclang
            libllvm
            libcxx
            cppcheck
          ];
        };
    };
}

